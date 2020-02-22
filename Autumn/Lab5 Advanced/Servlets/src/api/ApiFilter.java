package api;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter("/api/*")
public class ApiFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        resp.setContentType("application/json; charset=utf-8");
        if (req.getRequestURI().startsWith("/api/entrants")
                || req.getRequestURI().startsWith("/api/faculties")
                || req.getRequestURI().startsWith("/api/admins")) {
            filterChain.doFilter(req, resp);
        } else {
            resp.sendError(404);
        }
    }

    @Override
    public void destroy() {

    }
}