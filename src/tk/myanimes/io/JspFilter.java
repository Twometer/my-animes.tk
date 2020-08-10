package tk.myanimes.io;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "*.jsp")
public class JspFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (((HttpServletRequest) servletRequest).getServletPath().equals("/index.jsp"))
            servletRequest.getRequestDispatcher("/index.jsp").forward(servletRequest, servletResponse);
        ((HttpServletResponse) servletResponse).sendError(404);
    }

}
