package xyz.minic.xr.servlet;

import org.apache.commons.beanutils.BeanUtils;
import xyz.minic.xr.bean.Education;
import xyz.minic.xr.service.UserService;
import xyz.minic.xr.service.WebsiteService;
import xyz.minic.xr.service.impl.UserServiceImpl;
import xyz.minic.xr.service.impl.WebsiteServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author minic
 * @date 2022/07/15 22:40
 **/
@WebServlet("/education/*")
public class EducationServlet extends BaseServlet<Education> {
//    private EducationService service = new EducationServiceImpl();
    private UserService userService = new UserServiceImpl();
    private WebsiteService websiteService = new WebsiteServiceImpl();

    public void front(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("user", userService.list().get(0));
        request.setAttribute("footer", websiteService.list().get(0).getFooter());
        request.setAttribute("educations", service.list());
        forward(request, response, "front/education.jsp");
    }

    public void admin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("educations", service.list());
        forward(request, response, "admin/education.jsp");
    }

    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Education education = new Education();
        BeanUtils.populate(education, request.getParameterMap());

        if (service.save(education)) {
            redirect(request, response, "education/admin");
        } else {
            forwardError(request, response, "网站信息保存失败，请稍后再试");
        }
    }

    public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] idStrs = request.getParameterValues("id");
        List<Integer> ids = new ArrayList<>();
        for (String idStr : idStrs) {
            ids.add(Integer.valueOf(idStr));
        }
        if (service.remove(ids)) {
            redirect(request, response, "education/admin");
        } else {
            forwardError(request, response, "网站信息删除失败，请稍后再试");
        }
    }
}
