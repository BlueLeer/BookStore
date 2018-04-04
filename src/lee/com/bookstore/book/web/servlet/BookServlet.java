package lee.com.bookstore.book.web.servlet;

import cn.itcast.servlet.BaseServlet;
import lee.com.bookstore.book.domain.Book;
import lee.com.bookstore.book.service.BookService;
import lee.com.bookstore.category.domain.Category;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @CreateAuthor: KingIsHappy
 * @CreateDate: 2018/3/28
 * @Description:
 */
@WebServlet(name = "BookServlet", urlPatterns = "/BookServlet")
public class BookServlet extends BaseServlet {
    BookService mBookService = new BookService();

    public String findAll(HttpServletRequest request, HttpServletResponse response) {
        List<Book> books = mBookService.findAll();
        request.setAttribute("books", books);
        return "f:/jsps/book/list.jsp";
    }

    public String findByCid(HttpServletRequest request, HttpServletResponse response) {
        String cid = request.getParameter("cid");
        List<Book> books = mBookService.findByCid(cid);
        request.setAttribute("books", books);

        return "f:/jsps/book/list.jsp";
    }

    public String loadDesc(HttpServletRequest request, HttpServletResponse response) {
        String bid = request.getParameter("bid");
        Book book = mBookService.findBookByBid(bid);
        request.setAttribute("book", book);
        return "f:/jsps/book/desc.jsp";
    }
}
