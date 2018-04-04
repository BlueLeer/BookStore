package lee.com.bookstore.category.web.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import lee.com.bookstore.category.domain.Category;
import lee.com.bookstore.category.service.CategoryService;
import lee.com.bookstore.user.domain.AdminException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @CreateAuthor: KingIsHappy
 * @CreateDate: 2018/3/30
 * @Description:
 */
@WebServlet(name = "AdminCategoryServlet", urlPatterns = "/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {
    private CategoryService mCategoryService = new CategoryService();

    /*
    查看所有的分类
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response) {
        List<Category> categories = mCategoryService.findAll();
        request.setAttribute("categories", categories);
//
        return "f:/adminjsps/admin/category/list.jsp";
    }

    /*
    添加分类
     */
    public String add(HttpServletRequest request, HttpServletResponse response) {
        /*
        1. 获取参数 cname
        2.查询数据库是否已经存在该分类
        3.如果不存在该分类,补全category,调用service的add方法,添加到数据库
        4.调用findAll方法,该方法会查询最新的数据库分类信息,并且跳转到list.jsp
         */
        Category category = new Category(CommonUtils.uuid(), request.getParameter("cname"));
        try {
            mCategoryService.add(category);
            return findAll(request, response);
        } catch (AdminException e) {
            request.setAttribute("msg", e.getMessage());
            return "f:/adminjsps/admin/category/add.jsp";
        }
    }

    public String preUpdate(HttpServletRequest request, HttpServletResponse response) {
        String cid = request.getParameter("cid");
        String cname = request.getParameter("cname");
        Category category = new Category(cid, cname);

        request.setAttribute("category", category);

        return "f:/adminjsps/admin/category/mod.jsp";
    }

    public String update(HttpServletRequest request, HttpServletResponse response) {
        String cid = request.getParameter("cid");
        String cname = request.getParameter("cname");
        Category category = new Category(cid, cname);
        mCategoryService.update(category);
        return findAll(request, response);
    }

    public String preDelete(HttpServletRequest request, HttpServletResponse response) {
        String cid = request.getParameter("cid");
        String cname = request.getParameter("cname");
        Category category = new Category(cid, cname);

        request.setAttribute("category", category);

        return "f:/adminjsps/admin/category/del.jsp";
    }

    public String delete(HttpServletRequest request, HttpServletResponse response) {
        String cid = request.getParameter("cid");
        try {
            mCategoryService.delete(cid);
            return findAll(request, response);
        } catch (AdminException e) {
            request.setAttribute("msg", e.getMessage());
            Category category = mCategoryService.findByCid(cid);
            request.setAttribute("category", category);
            return "f:/adminjsps/admin/category/del.jsp";
        }
    }

}
