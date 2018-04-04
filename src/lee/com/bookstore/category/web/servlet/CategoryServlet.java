package lee.com.bookstore.category.web.servlet;

import cn.itcast.servlet.BaseServlet;
import lee.com.bookstore.category.domain.Category;
import lee.com.bookstore.category.service.CategoryService;

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
@WebServlet(name = "CategoryServlet", urlPatterns = "/CategoryServlet")
public class CategoryServlet extends BaseServlet {
    CategoryService mCategoryService = new CategoryService();

    public String findAll(HttpServletRequest request, HttpServletResponse response) {
        List<Category> categories = mCategoryService.findAll();
        request.setAttribute("categories", categories);
        return "f:/jsps/left.jsp";
    }


}
