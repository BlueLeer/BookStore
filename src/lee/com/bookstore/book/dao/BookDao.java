package lee.com.bookstore.book.dao;

import cn.itcast.jdbc.TxQueryRunner;
import lee.com.bookstore.book.domain.Book;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @CreateAuthor: KingIsHappy
 * @CreateDate: 2018/3/28
 * @Description:
 */
public class BookDao {
    QueryRunner mQueryRunner = new TxQueryRunner();

    public List<Book> findAll() {
        String sql = "select * from book";
        try {
            List<Book> books = mQueryRunner.query(sql, new BeanListHandler<>(Book.class));
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Book> findByCid(String cid) {
        String sql = "select * from book where cid = ?";
        List<Book> books = null;
        try {
            books = mQueryRunner.query(sql, new BeanListHandler<>(Book.class), cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    public Book findBookByBid(String bid) {
        String sql = "select * from book where bid = ?";
        try {
            return mQueryRunner.query(sql, new BeanHandler<>(Book.class), bid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int findCountByCid(String cid) {
        String sql = "select count(*) from book where cid = ?";
        try {
            Number count = mQueryRunner.query(sql, new ScalarHandler<>(), cid);
            return count.intValue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    注意:因为book表的主键作为orderitem表的外键,当抛出异常的时候说明不能删除,直接抛出异常
     */
    public void delete(String bid) throws SQLException {
        String sql = "delete from book where bid = ?";
        mQueryRunner.update(sql, bid);

    }

    public void update(Book book) {
        String sql = "update book set bname=?,price=?,author=?,cid=? where bid = ?";
        Object[] params = {book.getBname(), book.getPrice(), book.getAuthor(), book.getCid(), book.getBid()};
        System.out.println(params);
        try {
            mQueryRunner.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(Book book) throws SQLException {
        String sql = "insert into book values(?,?,?,?,?,?)";
        Object[] params = {book.getBid(), book.getBname(), book.getPrice(),
                book.getAuthor(), book.getImage(), book.getCid()};

        mQueryRunner.update(sql, params);
    }
}
