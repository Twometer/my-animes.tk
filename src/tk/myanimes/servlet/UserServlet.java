package tk.myanimes.servlet;

import tk.myanimes.anime.AnimeCache;
import tk.myanimes.anime.AnimeProvider;
import tk.myanimes.io.Database;
import tk.myanimes.model.WatchState;
import tk.myanimes.session.SessionManager;
import tk.myanimes.text.Formatter;
import tk.myanimes.text.Validator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.ZoneOffset;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    @Override
    protected void httpGet(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        var path = req.getPathInfo();
        if (path == null || path.isEmpty()) {
            resp.sendError(404);
            return;
        }

        var username = path.substring(1);
        if (!Validator.isValidUsername(username)) {
            resp.sendError(404);
            return;
        }

        var user = Database.findUserInfo(username);
        if (user == null) {
            resp.sendError(404);
            return;
        }

        req.setAttribute("formatter", new Formatter());
        req.setAttribute("animes", Database.getAnimeList(user));
        req.setAttribute("user", user);

        loadLoggedInUserInfo(req);
        req.getRequestDispatcher("/user.jsp").forward(req, resp);
    }

    @Override
    protected void httpPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!SessionManager.instance().isAuthenticated(req)) {
            resp.sendError(403);
            return;
        }

        var animeId = req.getParameter("animeId");
        var watchDate = req.getParameter("watchDate");
        var watchState = WatchState.parse(req.getParameter("watchState"));

        var storyRating = parseRating(req, watchState, "rating-story");
        var characterRating = parseRating(req, watchState, "rating-characters");
        var artRating = parseRating(req, watchState, "rating-art");
        var enjoymentRating = parseRating(req, watchState, "rating-enjoyment");

        if (watchState == WatchState.Queued) // Set date to maximum... not the prettiest solution but well
            watchDate = "9999-12-31";

        if (watchState == null || Validator.areNullOrEmpty(animeId, watchDate)) {
            resp.sendError(400);
            return;
        }

        var ratingNumeric = average(storyRating, characterRating, artRating, enjoymentRating);
        var watchDateInstant = LocalDate.parse(watchDate).atStartOfDay().toInstant(ZoneOffset.UTC);

        var kitsuAnime = AnimeProvider.instance().getKitsuAnimeInfo(Long.parseLong(animeId));
        if (kitsuAnime != null) {
            var user = SessionManager.instance().getCurrentUser(req);
            var anime = AnimeCache.instance().tryGetFullAnimeInfo(kitsuAnime);
            if (!Database.animeListContains(user, anime.getId()))
                Database.addToAnimeList(user, anime, ratingNumeric, watchDateInstant, watchState);
            resp.sendRedirect(req.getRequestURI());
            return;
        }

        resp.sendError(400);
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
