package lee.com.bookstore.book.web.servlet;

import cn.itcast.commons.CommonUtils;
import lee.com.bookstore.book.domain.Book;
import lee.com.bookstore.book.service.BookService;
import lee.com.bookstore.category.domain.Category;
import lee.com.bookstore.category.service.CategoryService;
import lee.com.bookstore.user.domain.AdminException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

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
@WebServlet(name = "AdminAddBookServlet", urlPatterns = "/AdminAddBookServlet")
public class AdminAddBookServlet extends HttpServlet {
    private BookService mBookService = new BookService();
    private CategoryService mCategoryService = new CategoryService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        /*
        1.获取request,将request解析成FIleItem
        2.解析每一项的值
        3.封装BooK对象
        4.调用service(Book book,FileItem fileitem)方法,将book插入到数据库
            >如果插入失败,保存错误信息到msg,转发到add.jsp
            >如果插入成功,保存成功信息,转发到/AdminBookServlet?method=findAll 显示图书列表
         */

        DiskFileItemFactory dff = new DiskFileItemFactory();
        ServletFileUpload fileUpload = new ServletFileUpload(dff);

        List<FileItem> fileItems = null;
        try {
            fileItems = fileUpload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        // 第一项对应的是普通的表单项:bname
        FileItem fileItem1 = fileItems.get(0);
        String bname = fileItem1.getString("UTF-8").trim();
        // 第二项对应的是file表单项:image
        FileItem fileItem2 = fileItems.get(1);
        String imageName = fileItem2.getName().trim(); //getName返回的是文件名,二getFieldName返回的是表单属性名
        // 第三项对应的是普通表单项:price
        FileItem fileItem3 = fileItems.get(2);
        String price = fileItem3.getString("UTF-8").trim();
        // 第四项对应的是普通表单项:author
        FileItem fileItem4 = fileItems.get(3);
        String author = fileItem4.getString("UTF-8").trim();
        // 第五项对应的是普通表单项:cid
        FileItem fileItem5 = fileItems.get(4);
        String cid = fileItem5.getString("UTF-8").trim();

        // 输入校验
        if (imageName.isEmpty() || author.isEmpty() || price.isEmpty() || bname.isEmpty()) {
            request.setAttribute("msg", "您输入的信息不完整,请重新输入!");
            List<Category> categories = mCategoryService.findAll();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(request, response);
            return;
        }

        Book book = new Book();
        book.setBid(CommonUtils.uuid());
        book.setBname(bname);
        book.setAuthor(author);
        book.setPrice(Double.parseDouble(price));
        book.setCid(cid);
        book.setImage(mBookService.getImageName(imageName));
        String context = request.getServletContext().getRealPath("/");

        try {
            mBookService.add(book, fileItem2, context);
            request.setAttribute("msg", "插入图书成功!");
            System.out.println("添加图书成功!");
            List<Book> books = mBookService.findAll();
            request.setAttribute("books", books);
            request.getRequestDispatcher("/adminjsps/admin/book/list.jsp").forward(request, response);
        } catch (AdminException e) {
            request.setAttribute("msg", e.getMessage());
            System.out.println("添加图书失败!");
            request.setAttribute("book", book);
            Category category = mCategoryService.findByCid(cid);
            request.setAttribute("category", category);
            request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(request, response);
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
