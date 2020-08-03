package tk.myanimes;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")
public class DashboardServlet extends BaseServlet {

    @Override
    protected boolean requiresAuthentication() {
        return true;
    }

    @Override
    protected void httpGet(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        loadLoggedInUserInfo(req);
        req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
    }

}
