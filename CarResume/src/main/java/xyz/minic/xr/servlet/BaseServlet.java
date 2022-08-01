package xyz.minic.xr.servlet;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import xyz.minic.xr.service.BaseService;
import xyz.minic.xr.service.impl.BaseServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

public abstract class BaseServlet<T> extends HttpServlet {
    // Date格式转为String   -> 放到listener里做 在项目启动（部署）的时候做一些一次性的操作（资源加载）
//    static {
//        // null参数表示允许值为null
//        DateConverter dateConverter = new DateConverter(null);
//        dateConverter.setPatterns(new String[]{"yyyy-MM-dd"});
//        ConvertUtils.register(dateConverter, Date.class);
//    }

    protected BaseService<T> service = newService();
    protected BaseService<T> newService() {
        // xyz.minic.xr.servlet.WebsiteServlet
        // xyz.minic.xr.service.impl.WebsiteServiceImpl
        try {
            String clsName = getClass().getName()
                    .replace(".servlet.", ".service.impl.")
                    .replace("Servlet", "ServiceImpl");
            return (BaseService<T>) Class.forName(clsName).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //设置编码
            request.setCharacterEncoding("utf-8");

            //获得uri，然后根据 / 来切割，获得请求路径是最后一个的方法
            String[] args = request.getRequestURI().split("/");
            String methodName = args[args.length - 1];
            //利用反射获得对象方法
            Method method = getClass()
                            .getMethod(methodName,
                            HttpServletRequest.class,
                            HttpServletResponse.class);
            //调用方法
            method.invoke(this, request, response);
        } catch (Exception e) {
            e.printStackTrace();

            //把异常显示到错误页面，更方便排错 02-22集
            // e一般是InvocationTargetException
            Throwable cause = e;
            while (cause.getCause() != null) {
                cause = cause.getCause();
            }

            forwardError(request, response, cause.getClass().getSimpleName() + ": " + cause.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    //转发
    protected void forward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/page/" + path).forward(request, response);
    }

    //重定向
    protected void redirect(HttpServletRequest request, HttpServletResponse response, String uri)  throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/"  + uri);
    }

    //转发至错误页面
    protected void forwardError(HttpServletRequest request, HttpServletResponse response,String error) throws ServletException, IOException {
        request.setAttribute("error", error);
        forward(request, response, "error.jsp");
    }


}
