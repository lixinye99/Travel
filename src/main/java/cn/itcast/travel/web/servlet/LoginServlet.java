package cn.itcast.travel.web.servlet;

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
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService =new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*获取表单中的数据*/
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String checkCode = req.getParameter("check");
        String rightCode = (String) req.getSession().getAttribute("CHECKCODE_SERVER");
        JSONObject errorinfo = userService.checkLoginInfo(username,password);

        if(errorinfo.getString("status") != null  && errorinfo.getString("status").equals("1")) {
            if(!checkCode.equals(rightCode)){
                errorinfo.put("checkCode","验证码不正确!");
                errorinfo.remove("status");
                errorinfo.remove("uid");
            }else {
                req.getSession().setAttribute("uid", errorinfo.getString("uid"));
            }
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Cache-Control", "no-cache");
        resp.getWriter().write(String.valueOf(errorinfo));
    }
}
