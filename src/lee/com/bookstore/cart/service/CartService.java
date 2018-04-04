package lee.com.bookstore.cart.service;

import lee.com.bookstore.book.dao.BookDao;
import lee.com.bookstore.book.domain.Book;
import lee.com.bookstore.cart.domain.CartItem;

/**
 * @CreateAuthor: KingIsHappy
 * @CreateDate: 2018/3/29
 * @Description:
 */
public class CartService {
    private BookDao mBookDao = new BookDao();

    public CartItem getCartItem(String bid, int count) {
        CartItem cartItem = new CartItem();
        Book book = mBookDao.findBookByBid(bid);
        cartItem.setBook(book);
        cartItem.setCount(count);
        return cartItem;
    }

}
