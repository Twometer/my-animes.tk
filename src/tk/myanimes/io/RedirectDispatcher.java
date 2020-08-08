package tk.myanimes.io;

import tk.myanimes.model.UserInfo;
import tk.myanimes.session.SessionManager;
import tk.myanimes.text.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RedirectDispatcher {

    public static void toRelative(HttpServletResponse resp, String relativePath) throws IOException {
        resp.sendRedirect(PathHelper.joinPath(AppConfig.instance().getBaseUrl(), AppConfig.instance().getBasePath(), relativePath));
    }

    public static void toReturnPage(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        toReturnPage(req, resp, SessionManager.instance().getCurrentUser(req));
    }

    public static void toReturnPage(HttpServletRequest req, HttpServletResponse resp, UserInfo user) throws IOException {
        var source = req.getParameter("src");
        if (user != null && !user.isSetupComplete())
            toRelative(resp, "/profile");
        else if (!Validator.nullOrEmpty(source) && source.startsWith("/"))
            resp.sendRedirect(PathHelper.joinPath(AppConfig.instance().getBaseUrl(), source));
        else if (user != null)
            toRelative(resp, "/dashboard");
        else
            toRelative(resp, "/");
    }


}
