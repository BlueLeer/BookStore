package lee.com.bookstore.cart.servlet;

import cn.itcast.servlet.BaseServlet;
import lee.com.bookstore.cart.domain.Cart;
import lee.com.bookstore.cart.domain.CartItem;
import lee.com.bookstore.cart.service.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @CreateAuthor: KingIsHappy
 * @CreateDate: 2018/3/29
 * @Description:
 */
@WebServlet(name = "CartServlet", urlPatterns = "/CartServlet")
public class CartServlet extends BaseServlet {
    private CartService mCartService = new CartService();

    public String addCartItem(HttpServletRequest request, HttpServletResponse response) {
        String bid = request.getParameter("bid");
        String count = request.getParameter("count");
        CartItem cartItem = mCartService.getCartItem(bid, Integer.parseInt(count));

        Cart cart = (Cart) request.getSession().getAttribute("session_cart");
        cart.add(cartItem);

//        request.getSession().setAttribute("session_cart", cart);

        return "f:/jsps/cart/list.jsp";
    }

    public String delete(HttpServletRequest request, HttpServletResponse response) {
        String bid = request.getParameter("bid");
        Cart cart = (Cart) request.getSession().getAttribute("session_cart");
        cart.getCartItemMap().remove(bid);
        return "f:/jsps/cart/list.jsp";
    }

    public String clear(HttpServletRequest request, HttpServletResponse response) {
        Cart cart = (Cart) request.getSession().getAttribute("session_cart");
        cart.clear();
        return "f:/jsps/cart/list.jsp";
    }
}
