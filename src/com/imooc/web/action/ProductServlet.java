package com.imooc.web.action;

import com.imooc.domain.Category;
import com.imooc.domain.Product;
import com.imooc.service.CategoryService;
import com.imooc.service.ProductService;
import com.imooc.service.impl.CategoryServiceImpl;
import com.imooc.service.impl.ProductServiceImpl;
import com.imooc.utils.UploadUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ProductServlet", urlPatterns = {"/ProductServlet"})
public class ProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");

        if ("findAll".equals(method)) {
            findAll(request, response);
        } else if ("saveUI".equals(method)) {
            saveUI(request, response);
        } else if ("save".equals(method)) {
            save(request, response);
        } else if ("edit".equals(method)) {
            edit(request, response);
        } else if ("update".equals(method)) {
            update(request, response);
        } else if ("delete".equals(method)) {
            delete(request, response);
        }
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer pid = Integer.parseInt(request.getParameter("pid"));
        ProductService productService = new ProductServiceImpl();
        Product product = productService.findOne(pid);
        String path = product.getPath();

        if (path != null && !"".equals(path)) {
            String realPath = this.getServletContext().getRealPath(path);
            System.out.println(realPath);
            File file = new File(realPath);
            if (file.exists()) {
                System.out.println("get in");
                file.delete();
            }
        }

        productService.delete(pid);
        response.sendRedirect(request.getContextPath() + "/ProductServlet?method=findAll");
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> map = UploadUtils.uploadFile(request);
        Category category = new Category();
        category.setCid(Integer.parseInt(map.get("cid")));
        Product product = new Product(Integer.parseInt(map.get("pid")),map.get("pname"), map.get("author"), Double.parseDouble(map.get("price")), map.get("description"), map.get("filename"), map.get("path"), category);
        ProductService productService = new ProductServiceImpl();
        productService.update(product);

        response.sendRedirect(request.getContextPath() + "/ProductServlet?method=findAll");
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer pid = Integer.parseInt(request.getParameter("pid"));
        ProductService productService = new ProductServiceImpl();
        Product product = productService.findOne(pid);

        CategoryService categoryService = new CategoryServiceImpl();
        List<Category> list = categoryService.findAll();

        request.setAttribute("categoryList", list);
        request.setAttribute("product", product);
        request.getRequestDispatcher("/admin/product_update.jsp").forward(request, response);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //  文件上传
        Map<String, String> map = UploadUtils.uploadFile(request);
        //  将数据完成封装
        Product product = new Product();
        product.setPname(map.get("pname"));
        product.setAuthor(map.get("author"));
        product.setPrice(Double.parseDouble(map.get("price")));
        product.setDescription(map.get("description"));
        product.setFilename(map.get("filename"));
        product.setPath(map.get("path"));
        product.getCategory().setCid(Integer.parseInt(map.get("cid")));

        ProductService productService = new ProductServiceImpl();
        productService.save(product);

        response.sendRedirect(request.getContextPath() + "/ProductServlet?method=findAll");
    }

    private void saveUI(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        CategoryService categoryService = new CategoryServiceImpl();
        List<Category> list = categoryService.findAll();
        request.setAttribute("categoryList", list);
        request.getRequestDispatcher("/admin/product_add.jsp").forward(request, response);
    }

    private void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService productService = new ProductServiceImpl();
        List<Product> list = productService.findAll();
        request.setAttribute("list", list);
        request.getRequestDispatcher("/admin/product_list.jsp").forward(request,response);
    }
}
