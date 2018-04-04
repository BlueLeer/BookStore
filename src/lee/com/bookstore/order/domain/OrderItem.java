package lee.com.bookstore.order.domain;

import lee.com.bookstore.book.domain.Book;
import lee.com.bookstore.user.domain.User;

/**
 * @CreateAuthor: KingIsHappy
 * @CreateDate: 2018/3/29
 * @Description:
 */
public class OrderItem {
    private String iid; // 订单条目的id
    private int count; // 图书数量
    private double total; // 小计
    private Order order; // 所属的订单
    private Book book; // 对应的数目

    public OrderItem() {
    }

    public OrderItem(String iid, int count, double total, Order order, Book book) {
        this.iid = iid;
        this.count = count;
        this.total = total;
        this.order = order;
        this.book = book;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "iid='" + iid + '\'' +
                ", count=" + count +
                ", total=" + total +
                ", order=" + order +
                ", book=" + book +
                '}';
    }
}
