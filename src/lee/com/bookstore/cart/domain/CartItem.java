package lee.com.bookstore.cart.domain;

import lee.com.bookstore.book.domain.Book;

import java.math.BigDecimal;

/**
 * @CreateAuthor: KingIsHappy
 * @CreateDate: 2018/3/29
 * @Description:
 */
public class CartItem {
    private Book book;
    private int count;

    public CartItem() {

    }

    public CartItem(Book book, int count) {
        this.book = book;
        this.count = count;
    }

    /*
    处理浮点数精度丢失的问题
     */
    public double getTotal() {
        double price = book.getPrice();
        BigDecimal bprice = new BigDecimal(price + "");
        BigDecimal bcount = new BigDecimal(count + "");

        return bcount.multiply(bprice).doubleValue();
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "book=" + book +
                ", count=" + count +
                '}';
    }
}

