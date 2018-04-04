package lee.com.bookstore.order.servlet;

import cn.itcast.servlet.BaseServlet;
import lee.com.bookstore.order.domain.Order;
import lee.com.bookstore.order.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @CreateAuthor: KingIsHappy
 * @CreateDate: 2018/3/31
 * @Description:
 */
@WebServlet(name = "AdminOrderServlet", urlPatterns = "/AdminOrderServlet")
public class AdminOrderServlet extends BaseServlet {
    private OrderService mOrderService = new OrderService();

    public String findAll(HttpServletRequest request, HttpServletResponse response) {
        /*
        1.查询所有的订单
        2.保存所有订单到request中
        3.转发到list.jsp
         */

        List<Order> orders = mOrderService.findAll();
        request.setAttribute("orders", orders);
        return "f:/adminjsps/admin/order/list.jsp";

    }


    public String findByState(HttpServletRequest request, HttpServletResponse response) {
        /*
        1.获取订单状态
        2.按条件查询所有的订单
        3.保存查询出来的订单到request中
        4.转发到list.jsp
         */

        String state = request.getParameter("state");
        List<Order> orders = mOrderService.findByState(state);
        request.setAttribute("orders", orders);

        return "f:/adminjsps/admin/order/list.jsp";
    }

}
