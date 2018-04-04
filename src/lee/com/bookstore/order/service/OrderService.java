package lee.com.bookstore.order.service;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.JdbcUtils;
import com.sun.org.apache.xpath.internal.operations.Or;
import lee.com.bookstore.cart.domain.Cart;
import lee.com.bookstore.cart.domain.CartItem;
import lee.com.bookstore.order.dao.OrderDao;
import lee.com.bookstore.order.domain.Order;
import lee.com.bookstore.order.domain.OrderItem;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @CreateAuthor: KingIsHappy
 * @CreateDate: 2018/3/29
 * @Description:
 */
public class OrderService {
    private OrderDao mOrderDao = new OrderDao();

    public List<Order> findAll() {
        return mOrderDao.finAll();
    }

    public List<Order> findByState(String state) {
        return mOrderDao.findByState(state);
    }

    public List<Order> findAllByUid(String uid) {
        return mOrderDao.findAllByUid(uid);
    }

    /*
    修改订单的状态
     */
    public void updateState(String oid, int state) {
        mOrderDao.updateState(oid, state);
    }


    public void addOrder(Order order) {
        /*
        1.将order的数据添加到数据库中
        2.将order下的orderItem中的数据添加到数据库中
         */
        try {
            JdbcUtils.beginTransaction();
            mOrderDao.addOrder(order);
            mOrderDao.addOrderItems(order.getOrderItems());
            JdbcUtils.commitTransaction();
        } catch (SQLException e) {
            try {
                JdbcUtils.rollbackTransaction();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

    }

    public Order getOrder(Cart cart, String uid) {
        Order order = new Order();
        order.setAddress("陕西省安康市汉滨区");
        order.setOid(CommonUtils.uuid());
        List<OrderItem> orderItems = new ArrayList<>();
        for (int i = 0; i < cart.getCartItems().size(); i++) {
            CartItem cartItem = (CartItem) ((Object[]) cart.getCartItems().toArray())[i];
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(cartItem.getBook());
            orderItem.setCount(cartItem.getCount());
            orderItem.setIid(CommonUtils.uuid());
            orderItem.setTotal(cartItem.getTotal());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        order.setState(1);
        order.setOrdertime(new Date());
        order.setTotal(cart.getTotal());

        return order;
    }

}
