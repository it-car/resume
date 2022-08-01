package xyz.minic.xr.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import xyz.minic.xr.bean.UploadParams;
import xyz.minic.xr.bean.User;
import xyz.minic.xr.service.AwardService;
import xyz.minic.xr.service.SkillService;
import xyz.minic.xr.service.UserService;
import xyz.minic.xr.service.WebsiteService;
import xyz.minic.xr.service.impl.AwardServiceImpl;
import xyz.minic.xr.service.impl.SkillServiceImpl;
import xyz.minic.xr.service.impl.WebsiteServiceImpl;
import xyz.minic.xr.util.Uploads;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * @author minic
 * @date 2022/07/22 16:14
 **/

@WebServlet("/user/*")
public class UserServlet extends BaseServlet<User> {
    private SkillService skillService = new SkillServiceImpl();
    private AwardService awardService = new AwardServiceImpl();
    private WebsiteService websiteService = new WebsiteServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       //为啥重写？  请求http://localhost:8081/xr 可以调到前台首页
        String uri = request.getRequestURI();
        String[] cmps = uri.split("/");
        String methodName = "/" + cmps[cmps.length - 1];
//        System.out.println(request.getContextPath()); // -> /xr
        if (methodName.equals(request.getContextPath())) {
            try {
                front(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            super.doGet(request, response);
        }
    }

    //前端首页
    public void front(HttpServletRequest request, HttpServletResponse response) throws Exception{
        // 用户信息
        User user = service.list().get(0);
        request.setAttribute("user", user);

        // 个人特质
        request.setAttribute("trait", user.getTrait().split(","));

        // 兴趣爱好
        request.setAttribute("interests", user.getInterests().split(","));

        // 专业技能
        request.setAttribute("skills", skillService.list());
        // 获奖成就
        request.setAttribute("awards", awardService.list());
        // 网站的底部信息
        request.setAttribute("footer", websiteService.list().get(0).getFooter());

        forward(request, response, "front/user.jsp");
    }

    public void admin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //查数据库，返回数据 跳转到后台管理首页
        request.setAttribute("user", service.list().get(0));
        forward(request, response, "admin/user.jsp");
    }

    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //需要保存图片
        UploadParams params = Uploads.parseRequest(request);

        User user = new User();
        BeanUtils.populate(user, params.getParams());

        //从session中获取用户的信息
        User loginUser = (User) request.getSession().getAttribute("user");
        user.setEmail(loginUser.getEmail());
        user.setPassword(loginUser.getPassword());

        //处理上传的图片
        FileItem item = params.getFileParam("photo");
        user.setPhoto(Uploads.uploadImage(item, request, user.getPhoto()));

        if (service.save(user)) {
            redirect(request, response, "user/admin");

            // 关键  更新session中的用户
            request.getSession().setAttribute("user", user);
        } else {
            forwardError(request, response, "个人信息保存失败");
        }
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //拿参数  ×  应该是设置编码
        response.setContentType("text/json; charset=UTF-8");
        Map<String, Object> result = new HashMap<>();  //为什么要new HashMap? 用来设置AJAX返回格式

        //判断验证码是否正确
        String captcha = request.getParameter("captcha").toLowerCase(); //获取用户提交的验证码
        String code = (String) request.getSession().getAttribute("code");  //获取存在服务器 session 中的验证码

        if (!captcha.equals(code)){ //不正确
            // forwardError(request, response, "验证码不正确");
            result.put("success", false);
            result.put("msg", "验证码不正确");
        }else {//正确
            //查数据库 判断用户和密码是否正确
            User user = new User();
            BeanUtils.populate(user, request.getParameterMap());
            user = ((UserService) service).get(user);

            if (user != null) {//正确  直接forward到admin
                //登录成功后，将User对象放入session中
                request.getSession().setAttribute("user", user);
                // redirect(request, response, "user/admin");
                result.put("success", true);
            } else {
                //不正确
                // forwardError(request, response, "邮箱或密码不正确");
                result.put("success", false);
                result.put("msg", "邮箱或密码不正确");
            }
        }

        Cookie cookie = new Cookie("JSESSIONID", request.getSession().getId());
        cookie.setMaxAge(3600 * 24 * 7);
        response.addCookie(cookie);

        //返回 AJAX请求需要的返回格式给前端
        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
    }

    //验证码 后端生成返回前端，前端在input框输入后通过Ajax连带账号密码发过来，后端检验是否正确
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //创建Katpcha 对象
        DefaultKaptcha dk = new DefaultKaptcha();

        //验证码的配置
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("kaptcha.properties")) {
            Properties properties = new Properties();
            properties.load(is);

            Config config = new Config(properties);
            dk.setConfig(config);
        }

        //生成验证码 字符串
        String code = dk.createText();

        //存储到session中（当这个客户端是首次请求服务器时，就会创建一个全新的Session）
        HttpSession session = request.getSession();
        session.setAttribute("code", code.toLowerCase());

        //生成验证码图片
        BufferedImage image = dk.createImage(code);

        //设置返回数据的格式
        response.setContentType("image/jpeg");

        //将图片数据写回到客服端
        ImageIO.write(image,"jpg", response.getOutputStream());
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //清除登录消息（将session中的用户删除）
        request.getSession().removeAttribute("user");

        //重定向到登录页面
        redirect(request, response, "page/login.jsp");
    }

    public void password(HttpServletRequest request, HttpServletResponse response) throws Exception {
        forward(request, response, "admin/password.jsp");
    }

    public void updatePassword(HttpServletRequest request, HttpServletResponse response)  throws Exception {
        String oldPassword = request.getParameter("oldPassword");
        // 对比session中用户的密码
        User user = (User) request.getSession().getAttribute("user");
        if (!user.getPassword().equals(oldPassword)) {
            forwardError(request, response, "旧密码不正确");
            return;
        }

        // 保存新密码
        String newPassword = request.getParameter("newPassword");
        user.setPassword(newPassword);
        if (service.save(user)) { // 保存成功
            redirect(request, response, "page/login.jsp");
        } else {
            forwardError(request, response, "修改密码失败");
        }

    }
}
