package lee.com.bookstore.book.service;

import cn.itcast.jdbc.JdbcUtils;
import com.sun.imageio.plugins.common.ImageUtil;
import lee.com.bookstore.book.dao.BookDao;
import lee.com.bookstore.book.dao.ImgUtils;
import lee.com.bookstore.book.domain.Book;
import lee.com.bookstore.user.domain.AdminException;
import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

/**
 * @CreateAuthor: KingIsHappy
 * @CreateDate: 2018/3/28
 * @Description:
 */
public class BookService {
    BookDao mBookDao = new BookDao();

    /**
     * 向数据库中添加图书,同时上传图书的图片到服务器指定的文件夹下
     *
     * @param book
     * @param item
     * @param context 图书图片存贮的目录
     */
    public void add(Book book, FileItem item, String context) throws AdminException {
        /*
        1.检查上传的图书图片文件是否过大
            >如果超过1MB抛出异常
        2.开启事务(需要将插入数据库的操作和上传文件的操作同时完成)
         */

        if (item.getSize() > 1024 * 1024) {
            System.out.println("文件过大!!");
            throw new AdminException("文件过大,请重新选择!");
        }

        try {
            JdbcUtils.beginTransaction();
            // 先想数据库写入图书信息
            mBookDao.add(book);
            // 再向服务器写入图书图片内容,如果写入失败,数据库插入也会回滚
            File imgFile = new File(context, book.getImage());
            InputStream in = item.getInputStream();
            ImgUtils.scale(in,imgFile.getPath(),150,100,true);
            JdbcUtils.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            throw new AdminException("插入图书信息失败!");
        }

    }

    public List<Book> findAll() {
        return mBookDao.findAll();
    }

    public List<Book> findByCid(String cid) {
        return mBookDao.findByCid(cid);
    }

    public Book findBookByBid(String bid) {
        return mBookDao.findBookByBid(bid);
    }

    public void delete(String bid) throws AdminException {
        try {
            mBookDao.delete(bid);
        } catch (SQLException e) {
            throw new AdminException("当前图书不能删除!");
        }
    }

    public void update(Book book) {
        mBookDao.update(book);
    }

    /*
    根据当前的时间生成一个唯一的图片名称
     */
    public String getImageName(String imageName) {
        long millis = System.currentTimeMillis();
        int i = imageName.lastIndexOf(".");
        String newImageName = "book_img/" + millis + imageName.substring(i);
        return newImageName;
    }
}
