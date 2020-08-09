package tk.myanimes.servlet;

import tk.myanimes.crypto.Credential;
import tk.myanimes.crypto.Hash;
import tk.myanimes.io.Database;
import tk.myanimes.io.RedirectDispatcher;
import tk.myanimes.model.UserInfo;
import tk.myanimes.servlet.base.BaseServlet;
import tk.myanimes.session.SessionManager;
import tk.myanimes.text.Validator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends BaseServlet {

    @Override
    protected void httpGet(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (req.getParameterMap().containsKey("logoff")) {
            SessionManager.instance().unregisterSession(req);
            RedirectDispatcher.toReturnPage(req, resp);
            return;
        }

        if (SessionManager.instance().isAuthenticated(req)) {
            RedirectDispatcher.toReturnPage(req, resp);
            return;
        }

        req.setAttribute("showLoginError", false);
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void httpPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (SessionManager.instance().isAuthenticated(req)) {
            RedirectDispatcher.toReturnPage(req, resp);
            return;
        }

        var username = req.getParameter("username");
        var password = req.getParameter("password");

        var user = tryAuthenticateUser(username, password);
        if (user == null) {
            req.setAttribute("showLoginError", true);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } else {
            SessionManager.instance().registerSession(req, user);
            RedirectDispatcher.toReturnPage(req, resp, user);
        }
    }

    private UserInfo tryAuthenticateUser(String username, String password) throws SQLException {
        if (Validator.nullOrEmpty(username, password) || !Validator.isValidUsername(username))
            return null;

        var user = Database.findUserInfo(username);
        if (user == null)
            return null;

        return Hash.validateHash(new Credential(username, password), user.getPasswordHash()) ? user : null;
    }

}
