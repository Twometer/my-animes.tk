package tk.myanimes;

import tk.myanimes.io.Database;
import tk.myanimes.model.*;
import tk.myanimes.session.SessionManager;
import tk.myanimes.text.Formatter;
import tk.myanimes.text.Validator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;

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

        var favAnime = new AnimeInfo();
        favAnime.setCanonicalTitle("DARLING in the FRANXX");
        favAnime.setCoverPicture("https://upload.wikimedia.org/wikipedia/en/d/dc/DARLING_in_the_FRANXX%2C_second_key_visual.jpg");
        favAnime.setAnimeStudios(new ArrayList<>());
        favAnime.getAnimeStudios().add("Trigger");
        favAnime.getAnimeStudios().add("A-1");
        favAnime.getAnimeStudios().add("CloverWorks");
        favAnime.setEpisodeCount(24);
        favAnime.setEpisodeLength(24);

        //var user = new UserInfo();
        //user.setName(username);
        //user.setBiography("Some text about yourself here");
        //user.setLocation("Germany");
        //user.setProfilePicture("https://i.pinimg.com/originals/92/f3/8b/92f38bfe1c3b5e3466908f57a3e27ca3.jpg");
        //user.setFavoriteAnime(favAnime);

        var animes = new AnimeList();

        var item = new AnimeListItem();
        item.setAnime(favAnime);
        item.setScore(10);
        item.setWatchDate(Instant.now());
        item.setWatchState(WatchState.Watched);
        animes.add(item);

        req.setAttribute("formatter", new Formatter());
        req.setAttribute("animes", animes);
        req.setAttribute("user", user);
        loadLoggedInUserInfo(req);
        req.getRequestDispatcher("/user.jsp").forward(req, resp);
    }

    private void loadLoggedInUserInfo(HttpServletRequest req) throws SQLException {
        if (SessionManager.instance().isAuthenticated(req)) {
            req.setAttribute("isAuthenticated", true);
            req.setAttribute("authenticatedUser", SessionManager.instance().getCurrentUser(req));
        } else {
            req.setAttribute("isAuthenticated", false);
            req.setAttribute("authenticatedUser", new UserInfo());
        }
    }

}
