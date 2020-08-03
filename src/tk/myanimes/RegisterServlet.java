package tk.myanimes;

import tk.myanimes.crypto.Credential;
import tk.myanimes.crypto.Hash;
import tk.myanimes.io.DataAccess;
import tk.myanimes.io.Database;
import tk.myanimes.model.UserInfo;
import tk.myanimes.session.SessionManager;
import tk.myanimes.text.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends BaseServlet {

    @Override
    protected void httpGet(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (SessionManager.instance().isAuthenticated(req)) {
            RedirectDispatcher.redirectToHomepage(req, resp);
            return;
        }

        sendResponse(req, resp, null);
    }

    @Override
    protected void httpPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (SessionManager.instance().isAuthenticated(req)) {
            resp.sendError(400);
            return;
        }

        var username = req.getParameter("username");
        var password = req.getParameter("password");
        var passwordConfirm = req.getParameter("passwordConfirm");

        if (Validator.areNullOrEmpty(username, password, passwordConfirm)) {
            sendResponse(req, resp, "Empty credentials are not allowed");
            return;
        }

        if (!Validator.isValidUsername(username)) {
            sendResponse(req, resp, "A username may only contain letters, numbers, _ and .");
            return;
        }

        if (password.length() < 6) {
            sendResponse(req, resp, "Password must be at least 6 characters long!");
            return;
        }

        if (!password.equals(passwordConfirm)) {
            sendResponse(req, resp, "Passwords do not match");
            return;
        }

        if (!DataAccess.instance().getUserDao().queryForEq("name", username).isEmpty()) {
            sendResponse(req, resp, "Username already taken");
            return;
        }

        var user = new UserInfo();
        user.setName(username);
        user.setPasswordHash(Hash.passwordHash(new Credential(username, password)));
        user.setSetupComplete(false);
        user.setLocation("Earth");
        Database.storeUserInfo(user);

        SessionManager.instance().registerSession(req, user);
        RedirectDispatcher.redirectToHomepage(resp, user);
    }

    private void sendResponse(HttpServletRequest req, HttpServletResponse resp, String error) throws ServletException, IOException {
        loadError(req, error);
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

}
