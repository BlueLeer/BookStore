package lee.com.bookstore.cart.domain;

import lee.com.bookstore.user.domain.User;

import java.math.BigDecimal;
import java.util.*;

/**
 * @CreateAuthor: KingIsHappy
 * @CreateDate: 2018/3/29
 * @Description:
 */
public class Cart {
    Map<String, CartItem> mCartItemMap = new LinkedHashMap<>();

    public void add(CartItem cartItem) {
        CartItem item = mCartItemMap.get(cartItem.getBook().getBid());
        if (item == null) {
            mCartItemMap.put(cartItem.getBook().getBid(), cartItem);
        } else {
            item.setCount(item.getCount() + cartItem.getCount());
            mCartItemMap.put(item.getBook().getBid(), item);
        }
    }

    public void delete(CartItem cartItem) {
        mCartItemMap.remove(cartItem.getBook().getBid());
    }

    public void clear() {
        mCartItemMap.clear();
    }

    public Map<String, CartItem> getCartItemMap() {
        return mCartItemMap;
    }

    public double getTotal() {
        BigDecimal total = new BigDecimal("0");
        for (CartItem item : mCartItemMap.values()) {
            total = total.add(new BigDecimal(item.getTotal() + ""));
        }

        return total.doubleValue();
    }

    public Collection<CartItem> getCartItems() {
        return mCartItemMap.values();
    }
}
