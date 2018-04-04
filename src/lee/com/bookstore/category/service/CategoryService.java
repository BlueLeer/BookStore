package lee.com.bookstore.category.service;

import cn.itcast.commons.CommonUtils;
import lee.com.bookstore.book.dao.BookDao;
import lee.com.bookstore.category.dao.CategoryDao;
import lee.com.bookstore.category.domain.Category;
import lee.com.bookstore.user.domain.AdminException;

import java.util.List;

/**
 * @CreateAuthor: KingIsHappy
 * @CreateDate: 2018/3/28
 * @Description:
 */
public class CategoryService {
    CategoryDao mCategoryDao = new CategoryDao();
    BookDao mBookDao = new BookDao();

    public List<Category> findAll() {
        return mCategoryDao.findAll();
    }

    public void add(Category category) throws AdminException {
        String cname = category.getCname();
        if (findByCname(cname) != null) {
            throw new AdminException("添加分类失败,已存在该分类!");
        }

        mCategoryDao.add(category);
    }

    public Category findByCname(String cname) {
        return mCategoryDao.findByCname(cname);
    }

    public Category findByCid(String cid) {
        return mCategoryDao.findByCid(cid);
    }

    /*
    删除分类
    注意事项,Category表的主键是Book表的外键,删除之前要检测
     */
    public void delete(String cid) throws AdminException {
        int count = mBookDao.findCountByCid(cid);
        if (count > 0) {
            throw new AdminException("该分类指向了图书,不能删除!");
        }

        mCategoryDao.delete(cid);
    }

    public void update(Category category) {
        mCategoryDao.update(category);
    }
}
