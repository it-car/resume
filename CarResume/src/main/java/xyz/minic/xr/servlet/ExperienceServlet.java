package xyz.minic.xr.servlet;

import org.apache.commons.beanutils.BeanUtils;
import xyz.minic.xr.bean.Company;
import xyz.minic.xr.bean.Experience;
import xyz.minic.xr.service.CompanyService;
import xyz.minic.xr.service.UserService;
import xyz.minic.xr.service.WebsiteService;
import xyz.minic.xr.service.impl.CompanyServiceImpl;
import xyz.minic.xr.service.impl.UserServiceImpl;
import xyz.minic.xr.service.impl.WebsiteServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author minic
 * @date 2022/07/22 14:12
 **/
@WebServlet("/experience/*")
public class ExperienceServlet extends BaseServlet<Experience> {
    private CompanyService companyService =  new CompanyServiceImpl();
    private UserService userService = new UserServiceImpl();
    private WebsiteService websiteService = new WebsiteServiceImpl();

    public void front(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("user", userService.list().get(0));
        request.setAttribute("footer", websiteService.list().get(0).getFooter());
        request.setAttribute("experiences", service.list());
        forward(request, response, "front/experience.jsp");
    }

    public void admin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("experiences", service.list());
        request.setAttribute("companies", companyService.list());
        forward(request, response, "admin/experience.jsp");
    }

    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Experience experience = new Experience();
        BeanUtils.populate(experience, request.getParameterMap());

        //对公司信息做特殊处理
        Company company = new Company();
        company.setId(Integer.valueOf(request.getParameter("companyId")));
        experience.setCompany(company);

        if (service.save(experience)) { // 保存成功
            redirect(request, response, "experience/admin");
        } else {
            forwardError(request, response, "工作经验保存失败");
        }
    }

    public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] idStrs = request.getParameterValues("id");
        List<Integer> ids = new ArrayList<>();
        for (String idStr : idStrs) {
            ids.add(Integer.valueOf(idStr));
        }
        // 删除
        if (service.remove(ids)) {
            redirect(request, response, "experience/admin");
        } else {
            forwardError(request, response, "工作经验删除失败");
        }
    }
}
