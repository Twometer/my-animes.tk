package tk.myanimes;

import tk.myanimes.model.UserInfo;
import tk.myanimes.session.SessionManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

public class RedirectDispatcher {

    private static final Logger log = Logger.getLogger(RedirectDispatcher.class.getName());

    public static void redirectToHomepage(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        var user = SessionManager.instance().getCurrentUser(req);
        redirectToHomepage(resp, user);
    }

    public static void redirectToHomepage(HttpServletResponse resp, UserInfo user) throws IOException {
        log.info("Redirecting: User = " + user);
        if (user == null)
            return;

        if (!user.isSetupComplete())
            resp.sendRedirect("profile");
        else
            resp.sendRedirect("dashboard");
    }

}
