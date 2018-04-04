package lee.com.bookstore.user.dao;

import cn.itcast.jdbc.TxQueryRunner;
import lee.com.bookstore.user.domain.User;
import lee.com.bookstore.user.domain.UserException;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * @CreateAuthor: KingIsHappy
 * @CreateDate: 2018/3/27
 * @Description:
 */
public class UserDao {
    private TxQueryRunner mTxQueryRunner = new TxQueryRunner();

    public User findUserByName(String userName) {
        String sql = "select * from tb_user where username=?";
        try {
            return mTxQueryRunner.query(sql, new BeanHandler<>(User.class), userName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findUserByEmail(String email) {
        String sql = "select * from tb_user where email=?";
        try {
            return mTxQueryRunner.query(sql, new BeanHandler<>(User.class), email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findUserByCode(String code) {
        String sql = "select * from tb_user where code = ?";
        try {
            return mTxQueryRunner.query(sql, new BeanHandler<>(User.class), code);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateState(User user,boolean state){
        String sql = "Update tb_user set state = ? where uid = ?";
        try {
            mTxQueryRunner.update(sql,state,user.getUid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        String sql = "insert into tb_user values(?,?,?,?,?,?)";
        Object[] params = {user.getUid(), user.getUsername(), user.getPassword(),
                user.getEmail(), user.getCode(), user.isState()};

        try {
            mTxQueryRunner.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
