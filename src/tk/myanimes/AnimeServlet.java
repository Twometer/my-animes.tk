package tk.myanimes;

import tk.myanimes.anime.AnimeProvider;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/anime/*")
public class AnimeServlet extends BaseServlet {

    @Override
    protected void httpGet(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        var path = req.getPathInfo().substring(1).split("/");
        var results = AnimeProvider.instance().searchAnime(path[0]);
        var result = results.get(Integer.parseInt(path[1]));
        var anime = AnimeProvider.instance().getFullInfo(result);
        resp.getWriter().println(anime.toString());
    }
}
