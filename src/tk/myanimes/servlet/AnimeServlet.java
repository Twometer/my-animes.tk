package tk.myanimes.servlet;

import tk.myanimes.anime.AnimeCache;
import tk.myanimes.text.Formatter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/anime/*")
public class AnimeServlet extends BaseServlet {

    @Override
    protected void httpGet(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        var path = req.getPathInfo().substring(1);
        var anime = AnimeCache.instance().tryGetFullAnimeInfoBySlug(path);
        if (anime == null)
            resp.sendError(404, "Slug does not exist");
        else {
            loadLoggedInUserInfo(req);
            req.setAttribute("anime", anime);
            req.setAttribute("formatter", new Formatter());
            req.getRequestDispatcher("/anime.jsp").forward(req, resp);
        }
    }
}
