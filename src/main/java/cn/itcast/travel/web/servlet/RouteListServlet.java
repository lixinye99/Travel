package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.impl.RouteListService;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Arno Clare
 */
@WebServlet("/routelist")
public class RouteListServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RouteListService service = new RouteListService();
        //获取 request 数据
        int pageIndex = Integer.parseInt(req.getParameter("pageNum"));
        int pageSize = Integer.parseInt(req.getParameter("pageSize"));
        //路线类别
        String category = req.getParameter("category");
        //名称搜索
        String name = req.getParameter("name");

        //获取 JSON
        JSONObject jsonObject;
        if (category != null && !category.equals("null")) {
            jsonObject = service.service("category", category, pageIndex, pageSize);
        } else if (name != null && !name.equals("null")) {
            jsonObject = service.service("name", name, pageIndex, pageSize);
        } else {
            jsonObject = null;
        }

        //配置 response
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Cache-Control", "no-cache");
        resp.getWriter().write(String.valueOf(jsonObject));
    }
}
