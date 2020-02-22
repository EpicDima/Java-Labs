import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession();
        Integer role = (Integer) session.getAttribute("role");
        if (role == null) {
            if (req.getRequestURI().equals("/authorization.html")
                    || req.getRequestURI().equals("/registration.html")
                    || req.getRequestURI().startsWith("/api/")) {
                filterChain.doFilter(req, resp);
            } else {
                resp.sendRedirect("/authorization.html");
            }
        } else {
            if (role.equals(1)) {
                if (req.getRequestURI().equals("/logout")
                        || req.getRequestURI().startsWith("/entrant/")) {
                    filterChain.doFilter(req, resp);
                } else {
                    resp.sendRedirect("/entrant/entrant.html");
                }
            } else if (role.equals(2)) {
                if (req.getRequestURI().equals("/logout")
                        || req.getRequestURI().startsWith("/admin/")) {
                    filterChain.doFilter(req, resp);
                } else {
                    resp.sendRedirect("/admin/admin.html");
                }
            }
        }
    }

    @Override
    public void destroy() {

    }
}
