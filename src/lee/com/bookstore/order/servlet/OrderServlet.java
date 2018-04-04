package lee.com.bookstore.order.servlet;

import cn.itcast.servlet.BaseServlet;
import lee.com.bookstore.cart.domain.Cart;
import lee.com.bookstore.order.domain.Order;
import lee.com.bookstore.order.service.OrderService;
import lee.com.bookstore.user.domain.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @CreateAuthor: KingIsHappy
 * @CreateDate: 2018/3/29
 * @Description:
 */
@WebServlet(name = "OrderServlet", urlPatterns = "/OrderServlet")
public class OrderServlet extends BaseServlet {
    OrderService mOrderService = new OrderService();

    /*
    添加订单
     */
    public String addOrder(HttpServletRequest request, HttpServletResponse response) {
        /*
        1.获取当前购物车中的cart
        2.通过cart生成order
            >通过cartItem生成orderItem
        3.将order添加到数据库
        4.将order设置给request
        5.转发到jsps/order/desc.jsp
         */
        Cart cart = (Cart) request.getSession().getAttribute("session_cart");
        User user = (User) request.getSession().getAttribute("session_user");
        String uid = user.getUid();
        Order order = mOrderService.getOrder(cart, uid);
        order.setUser(user);
        mOrderService.addOrder(order);
        request.setAttribute("order", order);

        // 清空购物车
        cart.clear();
        return "f:/jsps/order/desc.jsp";
    }

    /*
    查询所有的订单
     */
    public String findAllByUid(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("session_user");
        List<Order> orders = mOrderService.findAllByUid(user.getUid());
        request.setAttribute("orders", orders);
        return "f:jsps/order/list.jsp";
    }

    public String updateState(HttpServletRequest request, HttpServletResponse response) {
        String oid = request.getParameter("oid");
        int state = Integer.parseInt(request.getParameter("state"));
        mOrderService.updateState(oid, state);

        User user = (User) request.getSession().getAttribute("session_user");
        List<Order> orders = mOrderService.findAllByUid(user.getUid());
        request.setAttribute("orders", orders);

        return "f:jsps/order/list.jsp";
    }

    public String confirm(HttpServletRequest request, HttpServletResponse response) {
        String oid = request.getParameter("oid");
        int state = Integer.parseInt(request.getParameter("state"));
        mOrderService.updateState(oid, state);

        User user = (User) request.getSession().getAttribute("session_user");
        List<Order> orders = mOrderService.findAllByUid(user.getUid());
        request.setAttribute("orders", orders);

        return "f:jsps/order/list.jsp";
    }
}
