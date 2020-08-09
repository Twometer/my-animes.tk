package tk.myanimes.servlet;

import tk.myanimes.io.Database;
import tk.myanimes.servlet.base.BaseServlet;
import tk.myanimes.text.Validator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    @Override
    protected void httpGet(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        var path = req.getPathInfo();
        if (path == null || path.isEmpty()) {
            resp.sendError(404, "No username specified");
            return;
        }

        var username = path.substring(1);
        if (!Validator.isValidUsername(username)) {
            resp.sendError(404, "Not a valid username");
            return;
        }

        var user = Database.findUserInfo(username);
        if (user == null) {
            resp.sendError(404, "User does not exist");
            return;
        }

        req.setAttribute("animes", Database.getAnimeList(user));
        req.setAttribute("user", user);

        loadAuthenticatedUser(req);
        req.getRequestDispatcher("/user.jsp").forward(req, resp);
    }

}
