package tk.myanimes;

import tk.myanimes.crypto.Credential;
import tk.myanimes.crypto.Hash;
import tk.myanimes.io.Database;
import tk.myanimes.model.UserInfo;
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
        req.setAttribute("showLoginError", false);
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void httpPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        var username = req.getParameter("username");
        var password = req.getParameter("password");

        var user = tryAuthenticateUser(username, password);
        if (user == null) {
            req.setAttribute("showLoginError", true);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } else {
            SessionManager.instance().registerSession(req, user);
            resp.sendRedirect("dashboard");
        }
    }

    private UserInfo tryAuthenticateUser(String username, String password) throws SQLException {
        if (username == null || password == null || username.isEmpty() || password.isEmpty() || !Validator.isValidUsername(username))
            return null;

        var user = Database.findUserInfo(username);
        if (user == null)
            return null;

        return Hash.validateHash(new Credential(username, password), user.getPasswordHash()) ? user : null;
    }

}
