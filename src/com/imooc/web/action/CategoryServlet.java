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
}
