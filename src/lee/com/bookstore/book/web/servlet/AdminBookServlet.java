package lee.com.bookstore.book.web.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import lee.com.bookstore.book.dao.BookDao;
import lee.com.bookstore.book.domain.Book;
import lee.com.bookstore.book.service.BookService;
import lee.com.bookstore.category.domain.Category;
import lee.com.bookstore.category.service.CategoryService;
import lee.com.bookstore.user.domain.AdminException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @CreateAuthor: KingIsHappy
 * @CreateDate: 2018/3/30
 * @Description:
 */
@WebServlet(name = "AdminBookServlet", urlPatterns = "/AdminBookServlet")
public class AdminBookServlet extends BaseServlet {
    private BookService mBookService = new BookService();
    private CategoryService mCategoryService = new CategoryService();

    /*
    查询所有的图书分类,目的是在点击跳转到add.jsp页面的时候能够在添加页面的"图书分类"处显示正确的分类列表
     */
    public String findAllCategory(HttpServletRequest request, HttpServletResponse response) {
        /*
        1.查询所有的分类
        2.添加到request中
        3.转发到 /adminjsps/admin/book/add.jsp
         */

        List<Category> categories = mCategoryService.findAll();
        request.setAttribute("categories", categories);
        return "f:/adminjsps/admin/book/add.jsp";
    }

    public String del(HttpServletRequest request, HttpServletResponse response) {
        /*
        1.获取要删除图书的bid
        2.调用service方法进行删除
        3.* 如果删除没有出现异常,转发到 /adminjsps/admin/book/list.jsp
          * 如果删除出现异常,调用load()方法,同时报错错误信息到request

         */
        String bid = request.getParameter("bid");
        try {
            mBookService.delete(bid);
            return findAll(request, response);
        } catch (AdminException e) {
            request.setAttribute("msg", "当前图书不能删除!");
            return load(request, response);
        }
    }

    public String mod(HttpServletRequest request, HttpServletResponse response) {
        /*
        1.获取要更改图书的bid,封装到Book对象
        2.调用service方法进行更改
        3.更改成功 调用load方法,重新显示,同时保存成功信息到request(msg)
        */

        String bid = request.getParameter("bid");
        Book book = CommonUtils.toBean(request.getParameterMap(), Book.class);
        // 因为表单传过来的price带了一个 "元" 字,例如 45.60元,因此这里要进行特殊处理
        String price = request.getParameter("price");
        price = price.replace("元", "");
        book.setPrice(Double.parseDouble(price.trim()));

        mBookService.update(book);
        request.setAttribute("msg", "更改成功!");
        request.setAttribute("bid", bid);
        return load(request, response);
    }

    public String findAll(HttpServletRequest request, HttpServletResponse response) {
        /*
        1.查询所有图书
        2.保存图书列表到request
        2.跳转到 /adminjsps/admin/book/list.jsp
         */
        List<Book> books = mBookService.findAll();
        request.setAttribute("books", books);
        return "f:/adminjsps/admin/book/list.jsp";

    }

    public String load(HttpServletRequest request, HttpServletResponse response) {
        /*
        1.获取bid
        2.查询数据库,得到Book对象
        3.设置给request
        4.转发到 /adminjsps/admin/book/desc.jsp
         */

        String bid = request.getParameter("bid");
        Book book = mBookService.findBookByBid(bid);
        request.setAttribute("book", book);

        /*
        查询所有的分类,到desc的select下拉option中进行显示
         */
        List<Category> categories = mCategoryService.findAll();
        request.setAttribute("categories", categories);

        /*
        查询图书的分类名称,设置给request
         */

        Category current_category = mCategoryService.findByCid(book.getCid());
        request.setAttribute("current_category", current_category);
        return "f:/adminjsps/admin/book/desc.jsp";
    }

}
