package lee.com.bookstore.user.web.servlet;

import cn.itcast.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @CreateAuthor: KingIsHappy
 * @CreateDate: 2018/3/30
 * @Description:
 */
@WebServlet(name = "AdminUserServlet", urlPatterns = "/AdminUserServlet")
public class AdminUserServlet extends BaseServlet {
    public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        1.获取request表单中传递过来的登录信息
        2.从配置文件中获取系统分配的管理员账号信息
        3.进行比对,如果匹配,就允许登录,否则打回到login.jsp,显示错误信息
         */
        String form_adminname = request.getParameter("adminname");
        String form_password = request.getParameter("password");

        Properties prop = new Properties();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("admin_user.properties");
        prop.load(in);
        String adminname = prop.getProperty("adminname");
        String password = prop.getProperty("password");
        if (adminname.equals(form_adminname)) {
            if (password.equals(form_password)) {
                request.getSession().setAttribute("session_admin", adminname);
                return "/adminjsps/admin/index.jsp";
            } else {
                request.setAttribute("msg", "管理员密码错误!");
                return "adminjsps/login.jsp";
            }
        } else {
            request.setAttribute("msg", "管理员账户名错误!");
            return "adminjsps/login.jsp";
        }
    }

}
