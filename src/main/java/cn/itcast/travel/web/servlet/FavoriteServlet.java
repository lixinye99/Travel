package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.impl.FavoriteService;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 处理收藏请求
 * 未收藏则收藏，已收藏则取消收藏
 *
 * @author Arno Clare
 */
@WebServlet("/favorite")
public class FavoriteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*获取请求参数*/
        String rid = req.getParameter("rid");
        String uid = req.getSession().getAttribute("uid").toString();
        /*收藏处理*/
        FavoriteService fService = new FavoriteService();
        JSONObject jsonObject = fService.service(Integer.parseInt(rid), Integer.parseInt(uid));
        //配置 response
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Cache-Control", "no-cache");
        resp.getWriter().write(String.valueOf(jsonObject));
    }
}
