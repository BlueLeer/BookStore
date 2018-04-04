package lee.com.bookstore.order.dao;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import lee.com.bookstore.book.domain.Book;
import lee.com.bookstore.order.domain.Order;
import lee.com.bookstore.order.domain.OrderItem;
import lee.com.bookstore.user.domain.AdminException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @CreateAuthor: KingIsHappy
 * @CreateDate: 2018/3/29
 * @Description:
 */
public class OrderDao {

    private QueryRunner mQueryRunner = new TxQueryRunner();

    public List<Order> finAll() {
        String sql = "select * from orders";
        try {
            List<Order> orders = mQueryRunner.query(sql, new BeanListHandler<>(Order.class));
            for (Order o : orders) {
                List<OrderItem> orderItems = findAllOrderItems(o.getOid());
                o.setOrderItems(orderItems);
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Order> findByState(String state) {
        String sql = "select * from orders where state = ?";
        try {
            List<Order> orders = mQueryRunner.query(sql, new BeanListHandler<>(Order.class), state);
            for (Order o : orders) {
                List<OrderItem> orderItems = findAllOrderItems(o.getOid());
                o.setOrderItems(orderItems);
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateState(String oid, int state) {
        String sql = "Update orders set state = ? where oid = ?";
        try {
            mQueryRunner.update(sql, state, oid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    通过指定用户查询该用户的所有订单
     */
    public List<Order> findAllByUid(String uid) {
        String sql = "select * from orders where uid=?";
        try {
            List<Order> orderList = mQueryRunner.query(sql, new BeanListHandler<>(Order.class), uid);
            // 通过订单编号查询所有的订单条目
            for (Order o : orderList) {
                List<OrderItem> orderItems = findAllOrderItems(o.getOid());
                o.setOrderItems(orderItems);
            }
            return orderList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    查询下面所有的条目及其图书的详细信息
     */
    public List<OrderItem> findAllOrderItems(String oid) {
        // 每一个orderItem和book都有关系,所以进行多表连接查询
        String sql = "select * from book b inner join orderitem o where b.bid=o.bid and oid=?";
        List<OrderItem> orderItems = new ArrayList<>();
        try {
            List<Map<String, Object>> mapList = mQueryRunner.query(sql, new MapListHandler(), oid);
            for (Map m : mapList) {
                orderItems.add(toOrderItem(m));
            }
            return orderItems;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    将map中查询出来的信息封装成两个JavaBean对象,并将book设置给orderItem对象
     */
    public OrderItem toOrderItem(Map<String, Object> map) {
        OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
        Book book = CommonUtils.toBean(map, Book.class);
        orderItem.setBook(book);
        return orderItem;
    }

    public void addOrder(Order order) {
        String sql = "insert into orders values(?,?,?,?,?,?)";
        Object[] params = {order.getOid(), order.getOrdertime(), order.getTotal(),
                order.getState(), order.getUser().getUid(), order.getAddress()};

        try {
            mQueryRunner.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void addOrderItems(List<OrderItem> orderItems) {
        String sql = "insert into orderitem values(?,?,?,?,?)";
        Object[][] params = new Object[orderItems.size()][];
        for (int i = 0; i < orderItems.size(); i++) {
            OrderItem item = orderItems.get(i);
            params[i] = new Object[]{item.getIid(), item.getCount(), item.getTotal(), item.getOrder().getOid(), item.getBook().getBid()};
        }

        try {
            mQueryRunner.batch(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
