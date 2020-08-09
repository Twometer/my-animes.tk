package tk.myanimes.servlet;

import tk.myanimes.anime.AnimeCache;
import tk.myanimes.servlet.base.BaseServlet;
import tk.myanimes.text.Validator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/anime/*")
public class AnimeServlet extends BaseServlet {

    @Override
    protected void httpGet(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (req.getPathInfo() == null) {
            notFound(resp);
            return;
        }

        var path = req.getPathInfo().substring(1);
        if (Validator.nullOrEmpty(path)) {
            notFound(resp);
            return;
        }

        var anime = AnimeCache.instance().tryGetFullAnimeInfo(path);
        if (anime == null)
            notFound(resp);
        else {
            loadAuthenticatedUser(req);
            req.setAttribute("anime", anime);
            req.getRequestDispatcher("/anime.jsp").forward(req, resp);
        }
    }

    private void notFound(HttpServletResponse resp) throws IOException {
        resp.sendError(404, "Anime could not be found");
    }

}
