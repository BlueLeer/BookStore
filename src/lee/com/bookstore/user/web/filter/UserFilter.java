package lee.com.bookstore.user.web.filter;

import lee.com.bookstore.user.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @CreateAuthor: KingIsHappy
 * @CreateDate: 2018/3/31
 * @Description:
 */
@WebFilter(filterName = "UserFilter", urlPatterns = {"/lee/com/bookstore/cart/*", "/lee/com/bookstore/order/*"
        , "/CartServlet", "/OrderServlet"})
public class UserFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        User user = (User) request.getSession().getAttribute("session_user");
        if (user != null) {
            System.out.println("----------------------" + user.toString());
            chain.doFilter(req, resp);
        } else {
            request.getRequestDispatcher("/jsps/user/login.jsp").forward(request, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
