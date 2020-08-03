package tk.myanimes.servlet;

import tk.myanimes.anime.AnimeProvider;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/search")
public class SearchServlet extends BaseServlet {

    @Override
    protected void httpGet(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        var query = req.getParameter("q");
        if (query == null)
            resp.sendError(400);
        else {
            req.setAttribute("results", AnimeProvider.instance().searchAnime(query));
            req.getRequestDispatcher("/search.jsp").forward(req, resp);
        }
    }

}
