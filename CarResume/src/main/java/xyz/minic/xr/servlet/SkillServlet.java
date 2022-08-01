package xyz.minic.xr.servlet;

import org.apache.commons.beanutils.BeanUtils;
import xyz.minic.xr.bean.Skill;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author minic
 * @date 2022/07/16 22:08
 **/

@WebServlet("/skill/*")
public class SkillServlet extends BaseServlet<Skill> {
//    private SkillService service = new SkillServiceImpl();

    public void admin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("skills", service.list());
        forward(request, response, "admin/skill.jsp");
    }

    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Skill skill = new Skill();
        BeanUtils.populate(skill, request.getParameterMap());
        if (service.save(skill)) {
            response.sendRedirect(request.getContextPath() + "/skill/admin");
        } else {
            forwardError(request, response, "保存信息 失败，请稍后再试");
        }
    }

    public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] idStrs = request.getParameterValues("id");
        List<Integer> ids = new ArrayList<>();
        for (String idStr : idStrs) {
            ids.add(Integer.valueOf(idStr));
        }
        if (service.remove(ids)) {
            redirect(request, response, "skill/admin");
        } else {
            forwardError(request, response, "网站信息删除失败，请稍后再试");
        }
    }

}
