package tk.myanimes;

import tk.myanimes.model.UserInfo;
import tk.myanimes.session.SessionManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public abstract class BaseServlet extends HttpServlet {

    @Override
    protected final void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!tryAuthenticate(req, resp)) return;
        applyEncoding(req, resp);
        try {
            httpGet(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!tryAuthenticate(req, resp)) return;
        applyEncoding(req, resp);
        try {
            httpPost(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void httpGet(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        super.doGet(req, resp);
    }

    protected void httpPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        super.doPost(req, resp);
    }

    protected boolean requiresAuthentication() {
        return false;
    }

    protected final void loadLoggedInUserInfo(HttpServletRequest req) throws SQLException {
        if (SessionManager.instance().isAuthenticated(req)) {
            req.setAttribute("isAuthenticated", true);
            req.setAttribute("authenticatedUser", SessionManager.instance().getCurrentUser(req));
        } else {
            req.setAttribute("isAuthenticated", false);
            req.setAttribute("authenticatedUser", new UserInfo());
        }
    }

    protected final void loadError(HttpServletRequest req, String error) {
        req.setAttribute("showError", error != null);
        req.setAttribute("errorMessage", error != null ? error : "");
    }

    private void applyEncoding(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
    }

    private boolean tryAuthenticate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!requiresAuthentication())
            return true;
        if (SessionManager.instance().isAuthenticated(req))
            return true;
        resp.sendRedirect("login");
        return false;
    }


}
