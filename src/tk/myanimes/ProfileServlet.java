package tk.myanimes;

import tk.myanimes.io.DataAccess;
import tk.myanimes.io.Database;
import tk.myanimes.session.SessionManager;
import tk.myanimes.text.Validator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/profile")
public class ProfileServlet extends BaseServlet {

    @Override
    protected void httpGet(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        sendResponse(req, resp, null);
    }

    @Override
    protected void httpPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        var username = req.getParameter("username");
        var bio = req.getParameter("bio");
        var profilePicture = req.getParameter("profilepic");
        var location = req.getParameter("location");

        if (!Validator.isValidUsername(username)) {
            sendResponse(req, resp, "This is not a valid username!");
            return;
        }

        var user = SessionManager.instance().getCurrentUser(req);

        if (!username.equals(user.getName()) && DataAccess.instance().getUserDao().queryForEq("name", username).size() != 0) {
            sendResponse(req, resp, "Username already taken!");
            return;
        }

        user.setName(username);
        user.setBiography(bio);
        user.setProfilePicture(profilePicture);
        user.setLocation(location);
        user.setName(username);
        user.setSetupComplete(true);
        Database.storeUserInfo(user);
        RedirectDispatcher.redirectToHomepage(resp, user);
    }

    private void sendResponse(HttpServletRequest req, HttpServletResponse resp, String error) throws SQLException, ServletException, IOException {
        loadError(req, error);
        loadLoggedInUserInfo(req);
        req.getRequestDispatcher("/profile.jsp").forward(req, resp);
    }

    @Override
    protected boolean requiresAuthentication() {
        return true;
    }
}
