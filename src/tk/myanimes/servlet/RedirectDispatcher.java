package tk.myanimes.servlet;

import tk.myanimes.model.UserInfo;
import tk.myanimes.session.SessionManager;
import tk.myanimes.text.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RedirectDispatcher {

    public static void dispatch(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        var user = SessionManager.instance().isAuthenticated(req) ? SessionManager.instance().getCurrentUser(req) : null;
        dispatch(req, resp, user);
    }

    public static void dispatch(HttpServletRequest req, HttpServletResponse resp, UserInfo user) throws IOException {
        var source = req.getParameter("src");
        if (user != null && !user.isSetupComplete())
            resp.sendRedirect("profile");
        else if (!Validator.nullOrEmpty(source) && source.startsWith("/"))
            resp.sendRedirect(source);
        else if (user != null)
            resp.sendRedirect("dashboard");
        else
            resp.sendRedirect("/");
    }

}
