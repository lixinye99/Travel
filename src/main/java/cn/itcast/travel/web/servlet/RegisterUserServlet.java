package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.impl.UserService;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Create by lixinye
 */
@WebServlet("/registerUser")
public class RegisterUserServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        /*获取ajax传递过来的用户注册信息设置给新的user对象*/
        user.setUsername(username);
        user.setPassword(req.getParameter("password"));
        user.setEmail(email);
        user.setName(req.getParameter("name"));
        user.setTelephone(req.getParameter("telephone"));
        user.setSex(req.getParameter("sex"));
        user.setBirthday(req.getParameter("birthday"));
        String check = req.getParameter("check");
        String check_code = (String) req.getSession().getAttribute("CHECKCODE_SERVER");
        /*验证码以及用户信息的验证*/
        JSONObject errorinfo = userService.userInfoInuse(user);
        if (!check.equals(check_code)) {
            errorinfo.put("checkCode", "验证码输入错误!");
        }

        /*如果errorinfo不为空就直接返回页面，为空则创建新的用户*/
        if (errorinfo.isEmpty()) {
            int count = userService.insertUser(user);
            if (count == 1) {
                req.getSession().setAttribute("email",email);
                errorinfo.put("status", "1");
                resp.getWriter().write(String.valueOf(errorinfo));
            }
        } else {
            resp.setContentType("text/html;charset=utf-8");
            resp.setHeader("Cache-Control", "no-cache");
            resp.getWriter().write(String.valueOf(errorinfo));
        }
    }
}
