package lee.com.bookstore.user.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @CreateAuthor: KingIsHappy
 * @CreateDate: 2018/3/31
 * @Description:
 */
@WebFilter(filterName = "mAdminUserFilter", urlPatterns = {"/AdminAddBookServlet", "/AdminBookServlet",
        "/AdminOrderServlet", "/AdminCategoryServlet", "/adminjsps/admin/*"})
public class AdminUserFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String admin = (String) request.getSession().getAttribute("session_admin");
        if (admin != null) {
            chain.doFilter(req, resp);
        } else {
            request.getRequestDispatcher("/adminjsps/login.jsp").forward(request, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
