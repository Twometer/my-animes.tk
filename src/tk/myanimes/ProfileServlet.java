package tk.myanimes;

import javax.servlet.annotation.WebServlet;

@WebServlet("/profile")
public class ProfileServlet extends BaseServlet {

    @Override
    protected boolean requiresAuthentication() {
        return true;
    }
}
