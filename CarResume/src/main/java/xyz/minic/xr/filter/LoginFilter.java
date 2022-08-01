package xyz.minic.xr.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author minic
 * @date 2022/07/23 18:47
 **/
@WebFilter("/*")
public class LoginFilter implements Filter {
    /**
     * 当项目部署到Web容器时调用（当Filter被加载到Web容器中）
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 适合做一些资源的一次性加载、初始化
        System.out.println("LoginFilter - init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();

        // 优先放开的请求   因为css js等也会带有 admin 所以前面需要做多一个判断，不能用一个if else完成
        if (uri.contains("/asset/")
                || uri.contains("/contact/save")) {
            chain.doFilter(request, response);
        } else if (uri.contains("/admin")
                || uri.contains("/save")
                || uri.contains("/remove")
                || uri.contains("/user/password")
                || uri.contains("/user/updatePassword")) {
            // 需要作登录验证的请求
            Object user = request.getSession().getAttribute("user");
            if (user != null) { // 登录成功过
                chain.doFilter(request, response);
            } else { // 没有登录成功过
                response.sendRedirect(request.getContextPath() + "/page/login.jsp");
            }
        } else {
            chain.doFilter(request, response);
        }
    }
    /**
     * 当项目从Web容器中取消部署时调用（当Filter从Web容器中移除时调用）
     */
    @Override
    public void destroy() {
        // 适合做一些资源的回收操作
        System.out.println("LoginFilter - destroy");
    }
}
