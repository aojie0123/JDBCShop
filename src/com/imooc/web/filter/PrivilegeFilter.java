package com.imooc.web.filter;

import com.imooc.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "PrivilegeFilter", urlPatterns = {"/admin/*", "/CategoryServlet", "/ProductServlet"})
public class PrivilegeFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //  获得SESSION进行判断
        HttpServletRequest request = (HttpServletRequest) req;
        User existUser = (User)request.getSession().getAttribute("existUser");
        if (existUser == null) {
            //  没有登录
            request.setAttribute("msg", "您尚未登录，没有访问权限");
            request.getRequestDispatcher("/login.jsp").forward(request, (HttpServletResponse) resp);
            return;
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
