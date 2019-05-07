package com.imooc.web.action;

import com.imooc.domain.Category;
import com.imooc.service.CategoryService;
import com.imooc.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/CategoryServlet")
public class CategoryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //  接收method参数
        String method = request.getParameter("method");
        if ("findAll".equals(method)) {
            //  查询所有分类
            findAll(request, response);
        } else if ("saveUI".equals(method)) {
            saveUI(request, response);
        } else if ("save".equals(method)) {
            save(request, response);
        } else if ("edit".equals(method)) {
            edit(request, response);
        } else if ("update".equals(method)) {
            update(request, response);
        }
    }

    private void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //  接收参数
        //  封装数据
        //  调用业务层处理数据
        //  页面跳转
        CategoryService categoryService = new CategoryServiceImpl();
        List<Category> list = categoryService.findAll();
        request.setAttribute("list", list);
        request.getRequestDispatcher("/admin/category_list.jsp").forward(request, response);
    }

    private void saveUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/admin/category_add.jsp").forward(request, response);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cname = new String(request.getParameter("cname").getBytes("iso-8859-1"), "utf-8");
        String cdesc = request.getParameter("cdesc");

        //  数据封装
        Category category = new Category();
        category.setCname(cname);
        category.setCdesc(cdesc);

        //  调用业务层处理数据
        CategoryService categoryService = new CategoryServiceImpl();
        categoryService.save(category);

        //  页面跳转
        request.getRequestDispatcher(request.getContextPath() + "/CategoryServlet?method=findAll").forward(request, response);
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer cid = Integer.parseInt(request.getParameter("cid"));
        CategoryService categoryService = new CategoryServiceImpl();
        Category category = categoryService.findOne(cid);
        request.setAttribute("category", category);
        request.getRequestDispatcher("/admin/category_update.jsp").forward(request, response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        Integer cid = Integer.parseInt(request.getParameter("cid"));
        String cname = request.getParameter("cname");
        String cdesc = request.getParameter("cdesc");

        Category category = new Category(cid, cname, cdesc);
        System.out.println(category.toString());
        CategoryService categoryService = new CategoryServiceImpl();
        categoryService.update(category);

        response.sendRedirect(request.getContextPath() + "/CategoryServlet?method=findAll");
    }
}
