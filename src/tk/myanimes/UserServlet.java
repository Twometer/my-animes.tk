package tk.myanimes;

import tk.myanimes.model.*;
import tk.myanimes.text.Formatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;

@WebServlet("/user/*")
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var path = req.getPathInfo();
        if (path == null || path.isEmpty()) {
            resp.sendError(404);
            return;
        }

        var username = path.substring(1);
        if (!isValidUsername(username)) {
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

        var user = new UserInfo();
        user.setName("Twometer");
        user.setBiography("Some text about yourself here");
        user.setLocation("Germany");
        user.setProfilePicture("https://i.pinimg.com/originals/92/f3/8b/92f38bfe1c3b5e3466908f57a3e27ca3.jpg");
        user.setFavoriteAnime(favAnime);

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
        req.getRequestDispatcher("/user.jsp").forward(req, resp);
    }

    private boolean isValidUsername(String username) {
        for (var c : username.toCharArray())
            if (!Character.isLetterOrDigit(c))
                return false;
        return true;
    }

}
