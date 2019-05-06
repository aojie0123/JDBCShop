package com.imooc.web.action;

import com.imooc.domain.User;
import com.imooc.service.UserService;
import com.imooc.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //  接收参数
        String method = request.getParameter("method");
        //  判断
        if ("login".equals(method)) {
            login(request, response);
        }
    }

    /**
     * UserServlet 中的登录的方法
     * @param request
     * @param response
     */
    private void login(HttpServletRequest request, HttpServletResponse response) {
        //  接收用户名密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username + "   " + password);
        //  数据的封装
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        //  处理数据
        UserService userService = new UserServiceImpl();
        User existUser = userService.login(user);
        //  根据处理结果，完成页面跳转
        if (existUser == null) {
            //  登录失败
        } else {
            //  登录成功
        }
    }
}
