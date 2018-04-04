package lee.com.bookstore.user.service;

import lee.com.bookstore.user.dao.UserDao;
import lee.com.bookstore.user.domain.User;
import lee.com.bookstore.user.domain.UserException;

import java.sql.SQLException;

/**
 * @CreateAuthor: KingIsHappy
 * @CreateDate: 2018/3/27
 * @Description:
 */
public class UserService {
    private UserDao mUserDao = new UserDao();

    public void regist(User form) throws UserException {
        User user = mUserDao.findUserByName(form.getUsername());
        if (user != null) {
            throw new UserException("用户已被注册!");
        }

        user = mUserDao.findUserByEmail(form.getEmail());
        if (user != null) {
            throw new UserException("该邮箱已被注册!");
        }

        mUserDao.addUser(form);
    }

    /*
    激活(当用户点击邮箱中的激活链接的时候)
     */
    public void active(String code) throws UserException {
        User user = mUserDao.findUserByCode(code);
        if (user == null) {
            throw new UserException("您还没有注册,请不要进行激活!");
        }

        if (user.isState() == true) {
            throw new UserException("您已经激活啦,请登录吧!");
        }

        mUserDao.updateState(user, true);
    }

    /*
    登录
     */
    public User login(User form) throws UserException {
        /*
        1.从数据库查询用户
            >如果没有查询到,抛出异常(未注册)
        2.将数据库查询出来的用户密码和表单密码进行比对
            >如果密码不匹配抛出异常(密码错误)
        3.查询用户的激活状态
            >如果没有激活,抛出异常(未激活)
        4.如果上面几项验证都通过,返回查询查来的User
         */

        User user = mUserDao.findUserByName(form.getUsername());
        if (user == null) {
            throw new UserException("用户名不存在");
        }

        if (!form.getPassword().equals(user.getPassword())) {
            throw new UserException("用户密码错误!");
        }

        if (!user.isState()) {
            throw new UserException("用户未激活,请到邮箱中进行激活");
        }


        return user;
    }

}
