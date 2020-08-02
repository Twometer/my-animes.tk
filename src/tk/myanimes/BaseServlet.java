package tk.myanimes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public abstract class BaseServlet extends HttpServlet {

    @Override
    protected final void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        applyEncoding(req, resp);
        try {
            httpGet(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

    private void applyEncoding(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
    }

}
