package lee.com.bookstore.category.dao;

import cn.itcast.jdbc.TxQueryRunner;
import lee.com.bookstore.book.dao.BookDao;
import lee.com.bookstore.category.domain.Category;
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
public class CategoryDao {
    QueryRunner mQueryRunner = new TxQueryRunner();


    /**
     * 查询所有的分类
     *
     * @return
     */
    public List<Category> findAll() {
        String sql = "select * from category";
        try {
            List<Category> categoryList = mQueryRunner.query(sql, new BeanListHandler<>(Category.class));
            return categoryList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(Category category) {
        String sql = "insert into category values(?,?)";
        try {
            mQueryRunner.update(sql, category.getCid(), category.getCname());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Category findByCname(String cname) {
        String sql = "select * from category where cname = ?";
        try {
            return mQueryRunner.query(sql, new BeanHandler<>(Category.class), cname);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Category category) {
        String sql = "update category set cname=? where cid=?";
        try {
            mQueryRunner.update(sql, category.getCname(), category.getCid());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String cid) {
        String sql = "delete from category where cid = ?";
        try {
            mQueryRunner.update(sql, cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Category findByCid(String cid) {

        String sql = "select * from category where cid = ?";
        try {
            return mQueryRunner.query(sql, new BeanHandler<>(Category.class), cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
