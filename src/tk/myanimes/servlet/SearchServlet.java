package tk.myanimes.servlet;

import tk.myanimes.anime.AnimeProvider;
import tk.myanimes.text.Formatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/search")
public class SearchServlet extends BaseServlet {

    @Override
    protected void httpGet(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        var query = req.getParameter("q");

        if (query == null)
            resp.sendError(400, "Missing parameter 'q'");
        else
            sendWebsite(req, resp, query);
    }

    private void sendWebsite(HttpServletRequest req, HttpServletResponse resp, String query) throws IOException, ServletException, SQLException {
        req.setAttribute("results", AnimeProvider.instance().searchAnime(query));
        if ("true".equals(req.getParameter("raw")))
            req.getRequestDispatcher("/search_raw.jsp").forward(req, resp);
        else {
            req.setAttribute("query", query);
            req.setAttribute("formatter", new Formatter());
            loadLoggedInUserInfo(req);
            req.getRequestDispatcher("/search.jsp").forward(req, resp);
        }
    }

}
