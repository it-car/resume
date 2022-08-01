package xyz.minic.xr.servlet;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import xyz.minic.xr.bean.Company;
import xyz.minic.xr.bean.Project;
import xyz.minic.xr.bean.UploadParams;
import xyz.minic.xr.service.CompanyService;
import xyz.minic.xr.service.UserService;
import xyz.minic.xr.service.WebsiteService;
import xyz.minic.xr.service.impl.CompanyServiceImpl;
import xyz.minic.xr.service.impl.UserServiceImpl;
import xyz.minic.xr.service.impl.WebsiteServiceImpl;
import xyz.minic.xr.util.Uploads;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author minic
 * @date 2022/07/22 14:12
 **/

@WebServlet("/project/*")
public class ProjectServlet extends BaseServlet<Project> {
    private CompanyService companyService =  new CompanyServiceImpl();
    private UserService userService = new UserServiceImpl();
    private WebsiteService websiteService = new WebsiteServiceImpl();

    public void front(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("user", userService.list().get(0));
        request.setAttribute("footer", websiteService.list().get(0).getFooter());
        request.setAttribute("projects", service.list());
        forward(request, response, "front/project.jsp");
    }
    public void admin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("projects", service.list());
        request.setAttribute("companies", companyService.list());
        forward(request, response, "admin/project.jsp");
    }

    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {

        UploadParams uploadParams = Uploads.parseRequest(request);

        Project project = new Project();
        BeanUtils.populate(project, uploadParams.getParams());

        //对图片参数进行处理
        FileItem item = uploadParams.getFileParam("imageFile");
        project.setImage(Uploads.uploadImage(item, request, project.getImage()));

        //对公司信息做特殊处理
        Company company = new Company();
        company.setId(Integer.valueOf(uploadParams.getParam("companyId").toString()));
        project.setCompany(company);

        if (service.save(project)) { // 保存成功
            redirect(request, response, "project/admin");
        } else {
            forwardError(request, response, "项目经验保存失败");
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
            redirect(request, response, "project/admin");
        } else {
            forwardError(request, response, "项目经验删除失败");
        }
    }
}
