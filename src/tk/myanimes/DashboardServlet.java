package tk.myanimes;

import javax.servlet.annotation.WebServlet;

@WebServlet("/dashboard")
public class DashboardServlet extends BaseServlet {

    @Override
    protected boolean requiresAuthentication() {
        return true;
    }

}
