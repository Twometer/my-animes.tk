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
        sendResponse(req, resp, null);
    }

    @Override
    protected void httpPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        var username = req.getParameter("username");
        var password = req.getParameter("password");
        var passwordConfirm = req.getParameter("passwordConfirm");

        if (Validator.areNullOrEmpty(username, password, passwordConfirm)) {
            sendResponse(req, resp, "Empty credentials are not allowed");
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
        Database.storeUserInfo(user);

        SessionManager.instance().registerSession(req, user);
        resp.sendRedirect("profile");
    }

    private void sendResponse(HttpServletRequest req, HttpServletResponse resp, String error) throws ServletException, IOException {
        req.setAttribute("showError", error != null);
        req.setAttribute("errorMessage", error != null ? error : "");
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }
}
