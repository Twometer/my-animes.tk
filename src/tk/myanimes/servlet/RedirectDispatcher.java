package tk.myanimes.servlet;

import tk.myanimes.model.UserInfo;
import tk.myanimes.session.SessionManager;
import tk.myanimes.text.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

public class RedirectDispatcher {

    private static final Logger log = Logger.getLogger(RedirectDispatcher.class.getName());

    public static void redirectToHomepage(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        var user = SessionManager.instance().getCurrentUser(req);
        redirectToHomepage(req, resp, user);
    }

    public static void redirectToHomepage(HttpServletRequest req, HttpServletResponse resp, UserInfo user) throws IOException {
        log.info("Redirecting: User = " + user);
        if (user == null)
            return;

        var source = req.getParameter("src");

        if (!user.isSetupComplete())
            resp.sendRedirect("profile");
        else if (!Validator.nullOrEmpty(source) && source.startsWith("/"))
            resp.sendRedirect(source);
        else
            resp.sendRedirect("dashboard");
    }

}
