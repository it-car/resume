package xyz.minic.xr.servlet;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import xyz.minic.xr.bean.Award;
import xyz.minic.xr.bean.UploadParams;
import xyz.minic.xr.util.Uploads;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author minic
 * @date 2022/07/21 10:05
 **/

@WebServlet("/award/*")
public class AwardServlet extends BaseServlet<Award> {
//    private AwardService service  = new AwardServiceImpl();

    public void admin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("awards", service.list());
        forward(request, response, "admin/award.jsp");
    }


    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        UploadParams uploadParams = Uploads.parseRequest(request);

        Award award =  new Award();
        BeanUtils.populate(award, uploadParams.getParams()); //非文件参数可以直接转

        FileItem item  = uploadParams.getFileParam("imageFile");
        award.setImage(Uploads.uploadImage(item, request, award.getImage()));

        if (service.save(award)) { // 保存成功
            redirect(request, response, "award/admin");
        } else {
            forwardError(request, response, "获奖成就保存失败");
        }
    }

    public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获得客户端传过来的所有id值
        String[] idStrs = request.getParameterValues("id");
        List<Integer> ids = new ArrayList<>();
        for (String idStr : idStrs) {
            ids.add(Integer.valueOf(idStr));
        }
        // 删除
        if (service.remove(ids)) {
            redirect(request, response, "award/admin");
        } else {
            forwardError(request, response, "获奖成就删除失败");
        }
    }
}
