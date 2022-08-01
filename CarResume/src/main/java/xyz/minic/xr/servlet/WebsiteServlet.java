package xyz.minic.xr.servlet;


import org.apache.commons.beanutils.BeanUtils;
import xyz.minic.xr.bean.Website;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author minic
 * @date 2022/07/14 10:18
 **/

@WebServlet("/website/*")
public class WebsiteServlet extends BaseServlet<Website> {
//    private WebsiteService service = new WebsiteServiceImpl();

    public void admin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Website> websites = service.list();
        Website website = (websites != null && !websites.isEmpty()) ? websites.get(0) : null;

        request.setAttribute("website", website);
        forward(request, response, "admin/website.jsp");
    }


    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Website website = new Website();
        //把客户端传来的参数 -> bean模型
        BeanUtils.populate(website, request.getParameterMap());
        if (service.save(website)) {
            redirect(request, response, "website/admin");
        }else {
            forwardError(request, response, "网站信息保存失败，请稍后再试");
        }
    }

}
