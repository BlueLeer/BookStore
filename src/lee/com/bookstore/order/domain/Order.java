package lee.com.bookstore.order.domain;

import lee.com.bookstore.user.domain.User;

import java.util.Date;
import java.util.List;

/**
 * @CreateAuthor: KingIsHappy
 * @CreateDate: 2018/3/29
 * @Description:
 */
public class Order {
    private String oid;
    private Date ordertime;
    private double total;
    private int state;
    private User user;
    private String address;
    private List<OrderItem> orderItems;

    public Order() {
    }

    public Order(String oid, Date ordertime, double total, int state, User user, String address) {
        this.oid = oid;
        this.ordertime = ordertime;
        this.total = total;
        this.state = state;
        this.user = user;
        this.address = address;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Order{" +
                "oid='" + oid + '\'' +
                ", ordertime=" + ordertime +
                ", total=" + total +
                ", state=" + state +
                ", user=" + user +
                ", address='" + address + '\'' +
                '}';
    }
}
