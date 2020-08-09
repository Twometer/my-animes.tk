package tk.myanimes.servlet;

import tk.myanimes.servlet.base.AuthenticationMode;
import tk.myanimes.servlet.base.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")
public class DashboardServlet extends BaseServlet {

    @Override
    protected AuthenticationMode getAuthenticationMode() {
        return AuthenticationMode.Redirect;
    }

    @Override
    protected void httpGet(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        loadAuthenticatedUser(req);
        req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
    }

}
