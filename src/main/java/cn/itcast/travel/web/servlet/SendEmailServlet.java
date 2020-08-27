package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.impl.UserService;
import cn.itcast.travel.util.MailUtils;
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
@WebServlet("/sendEmail")
public class SendEmailServlet extends HttpServlet {

    UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject object = new JSONObject();
        String email = req.getSession().getAttribute("email").toString();
        boolean mail = MailUtils.sendMail(email, "恭喜您，注册成功!", "注册成功验证信息!");
        if(mail == true){
           object.put("status","1");
        }else {
            int count = userService.deleteUserByEmail(email);
            object.put("status","0");
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Cache-Control", "no-cache");
        resp.getWriter().write(String.valueOf(object));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
