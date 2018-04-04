package lee.com.bookstore.user.web.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.MailUtils;
import cn.itcast.servlet.BaseServlet;
import lee.com.bookstore.cart.domain.Cart;
import lee.com.bookstore.user.domain.User;
import lee.com.bookstore.user.domain.UserException;
import lee.com.bookstore.user.service.UserService;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @CreateAuthor: KingIsHappy
 * @CreateDate: 2018/3/27
 * @Description:
 */
@WebServlet(name = "UserServlet", urlPatterns = "/UserServlet")
public class UserServlet extends BaseServlet {
    private UserService mUserService = new UserService();

    public String quit(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return "f:/index.jsp";
    }

    public String login(HttpServletRequest request, HttpServletResponse response) {
        User form = CommonUtils.toBean(request.getParameterMap(), User.class);
        try {
            User user = mUserService.login(form);
            request.getSession().setAttribute("session_user", user);

            // 创建购物车(以当前登录的用户的uid作为键来保存)
            request.getSession().setAttribute("session_cart", new Cart());
        } catch (UserException e) {
            request.setAttribute("msg", e.getMessage());
            request.setAttribute("form", form);
            return "f:/jsps/user/login.jsp";
        }
        return "f:/index.jsp";
    }

    /*
    用户激活
     */
    public String active(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        try {
            mUserService.active(code);
            request.setAttribute("msg", "恭喜您,激活成功,请登录吧!");
        } catch (UserException e) {
            request.setAttribute("msg", e.getMessage());
        }

        return "f:/jsps/msg.jsp";
    }

    /*
    用户注册
     */
    public String regist(HttpServletRequest request, HttpServletResponse response) {
        /*
        1.获取表单数据
        2.封装表单数据,并补全
        3.校验表单数据
            > 如果有误,保存错误信息和form表单数据内容到request,然后转发到"regist.jsp"
        4.调用service的regist()方法
            > 如果抛出异常,保存错误信息和form表单数据内容到request,然后转发到"regist.jsp"
        5.完成注册,跳转到msg.jsp页面
        6.提示用户去邮箱进行激活
         */
        User form = CommonUtils.toBean(request.getParameterMap(), User.class);

        form.setUid(CommonUtils.uuid());
        form.setCode(CommonUtils.uuid() + CommonUtils.uuid());

        // 输入校验
        // 将错误信息封装到map中
        Map<String, String> errors = new HashMap<>();
        if (form.getUsername() == null || form.getUsername().trim().isEmpty()) {
            errors.put("username", "用户名不能为空!");
        }
        if (form.getPassword() == null || form.getPassword().trim().isEmpty()) {
            errors.put("password", "密码不能为空");
        }
        if (form.getEmail() == null || form.getEmail().trim().isEmpty()) {
            errors.put("email", "邮箱不能为空!");
        }

        if (errors.size() > 0) {
            request.setAttribute("errors", errors);
            request.setAttribute("form", form);
            return "f:/jsps/user/regist.jsp";
        }


        try {
            mUserService.regist(form);
        } catch (UserException e) {
            request.setAttribute("msg", e.getMessage());
            request.setAttribute("form", form);
            return "f:/jsps/user/regist.jsp";
        }

        request.setAttribute("msg", "恭喜您,注册成功,请前往邮箱进行激活!");

        // 发送激活邮件
        // 读取配置文件
        try {
            Properties prop = new Properties();
//            prop.load(this.getClass().getClassLoader().getResourceAsStream("email_activation.properties"));
            prop.load(new InputStreamReader(this.getClass().getClassLoader()
                    .getResourceAsStream("email_activation.properties"), "UTF-8"));
            String host = prop.getProperty("host");
            String uname = prop.getProperty("uname");
            String psd = prop.getProperty("psd");
            String from = prop.getProperty("from");
            String subject = prop.getProperty("subject");
            String content = prop.getProperty("content");
            // 填充配置文件中的参数部分
            content = MessageFormat.format(content, form.getCode());

            Properties p = new Properties();
            p.setProperty("mail.smtp.host", host); // 设置主机名
            p.setProperty("mail.smtp.auth", "true"); // 设置需要验证
            Authenticator authen = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    PasswordAuthentication pas = new PasswordAuthentication(uname, psd);
                    return pas;
                }
            };
            Session session = Session.getInstance(p, authen);
            session.setDebug(true);
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setSubject(subject);
            msg.setContent(content, "text/html;charset=UTF-8");
            msg.addRecipients(Message.RecipientType.TO, form.getEmail());

            Transport.send(msg);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "f:/jsps/msg.jsp";
    }
}
