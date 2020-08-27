package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.impl.RouteDetailService;
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
@WebServlet("/showRouteDetail")
public class RouteDetailServlet extends HttpServlet {
    RouteDetailService routeDetailService = new RouteDetailService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject json = new JSONObject();
        int rid = Integer.parseInt(req.getParameter("rid"));
        Route route = routeDetailService.querySingle(rid);
        Seller seller = routeDetailService.querysellyByRoute(route);
        String beanString = JSONObject.toJSONString(route);
        String sellerString = JSONObject.toJSONString(seller);
        json.put("detail",beanString);
        json.put("seller",sellerString);
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Cache-Control", "no-cache");
        resp.getWriter().write(String.valueOf(json));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
