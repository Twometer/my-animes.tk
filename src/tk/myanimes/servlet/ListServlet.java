package tk.myanimes.servlet;

import tk.myanimes.anime.AnimeCache;
import tk.myanimes.io.DataAccess;
import tk.myanimes.io.Database;
import tk.myanimes.io.RedirectDispatcher;
import tk.myanimes.model.UserInfo;
import tk.myanimes.model.WatchState;
import tk.myanimes.session.SessionManager;
import tk.myanimes.text.Parser;
import tk.myanimes.text.Validator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;

@WebServlet("/list")
public class ListServlet extends BaseServlet {

    @Override
    protected void httpPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!SessionManager.instance().isAuthenticated(req)) {
            resp.sendError(403);
            return;
        }

        var action = req.getParameter("action");
        if (Validator.nullOrEmpty(action)) {
            resp.sendError(400, "Missing required parameter 'action'");
            return;
        }

        if (action.equalsIgnoreCase("add")) {
            handleAddRequest(req, resp);
        } else if (action.equalsIgnoreCase("delete")) {
            handleDeleteRequest(req, resp);
        } else if (action.equalsIgnoreCase("edit")) {
            handleEditRequest(req, resp);
        } else {
            resp.sendError(400, "Invalid action");
        }
    }

    private void handleDeleteRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        var animeId = req.getParameter("animeId");
        if (Validator.nullOrEmpty(animeId)) {
            resp.sendError(400, "Missing required parameter 'animeId'");
            return;
        }
        var user = SessionManager.instance().getCurrentUser(req);
        Database.removeFromAnimeList(user, Long.parseLong(animeId));
        redirectToList(req, resp, user);
    }

    private void handleEditRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        var request = parseListRequest(req);
        if (request == null) {
            resp.sendError(400, "Missing required parameters");
            return;
        }
        var user = SessionManager.instance().getCurrentUser(req);
        var item = Database.getFromAnimeList(user, request.animeId);

        if (item == null) {
            resp.sendError(400, "No such anime list item");
            return;
        }
        item.setScore(request.rating);
        item.setWatchDate(request.watchDate.toEpochMilli());
        item.setWatchState(request.watchState);
        DataAccess.instance().getAnimeListItemDao().update(item);
        redirectToList(req, resp, user);
    }

    private void handleAddRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        var request = parseListRequest(req);
        if (request == null) {
            resp.sendError(400, "Missing required parameters");
            return;
        }

        if (Validator.nullOrEmpty(request.animeSlug)) {
            resp.sendError(400, "Missing required parameter 'animeSlug'");
            return;
        }

        var animeInfo = AnimeCache.instance().tryGetFullAnimeInfoBySlug(request.animeSlug);
        if (animeInfo != null) {
            var user = SessionManager.instance().getCurrentUser(req);
            if (!Database.animeListContains(user, animeInfo.getId()))
                Database.addToAnimeList(user, animeInfo, request.rating, request.watchDate, request.watchState);
            redirectToList(req, resp, user);
            return;
        }
        resp.sendError(400, "Anime does not exist");
    }

    private ListRequest parseListRequest(HttpServletRequest req) {
        var request = new ListRequest();
        request.animeSlug = req.getParameter("animeSlug");
        var watchDate = req.getParameter("watchDate");
        var watchState = WatchState.parse(req.getParameter("watchState"));

        var animeIdRaw = req.getParameter("animeId");
        if (!Validator.nullOrEmpty(animeIdRaw))
            request.animeId = Long.parseLong(animeIdRaw);

        var storyRating = parseRating(req, watchState, "rating-story");
        var characterRating = parseRating(req, watchState, "rating-characters");
        var artRating = parseRating(req, watchState, "rating-art");
        var enjoymentRating = parseRating(req, watchState, "rating-enjoyment");

        if (watchState == WatchState.Queued) // Set date to maximum... not the prettiest solution but well
            watchDate = "9999-12-31";

        if (watchState == null || Validator.nullOrEmpty(watchDate)) {
            return null;
        }

        request.rating = average(storyRating, characterRating, artRating, enjoymentRating);
        request.watchDate = Parser.parseDate(watchDate);
        request.watchState = watchState;
        return request;
    }

    private static class ListRequest {
        long animeId;
        String animeSlug;
        Instant watchDate;
        WatchState watchState;
        float rating;
    }

    private void redirectToList(HttpServletRequest req, HttpServletResponse resp, UserInfo user) throws IOException {
        RedirectDispatcher.toRelative(resp, "/user/" + user.getName());
    }

    private long parseRating(HttpServletRequest request, WatchState watchState, String name) {
        if (watchState != WatchState.Watched)
            return -1;
        else
            return Long.parseLong(request.getParameter(name));
    }

    private float average(float... data) {
        float total = 0;
        for (var d : data)
            total += d;
        return total / data.length;
    }

}
