package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.impl.FavoriteService;
import cn.itcast.travel.service.impl.RouteDetailService;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Create by lixinye
 */
@WebServlet("/myFavorite")
public class MyFavoriteServlet extends HttpServlet {
    private FavoriteService favoriteService = new FavoriteService();
    private RouteDetailService routeDetailService = new RouteDetailService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int uid = -1;
        boolean flag = true;
        JSONObject jsonObject = new JSONObject();
        int pageNum = Integer.parseInt(req.getParameter("pageNum"));
        if(req.getSession().getAttribute("uid")== "" || req.getSession().getAttribute("uid") == null ){
            jsonObject.put("unlogin","你还没有登录!快去进行登录吧!");
            flag = false;
        }else{
            uid = Integer.parseInt(req.getSession().getAttribute("uid").toString());
        }
        int pageCount = favoriteService.queryPageCount(uid);
        if(pageCount<pageNum){
            jsonObject.put("pageerror","选择页码有误!请检查页码!");
            flag = false;
        }else{
            jsonObject.put("pageCount",pageCount);
        }
        if(flag)
        {
            List<Integer> list = favoriteService.queryByUidAndPageNum(uid, pageNum);
            List<Route> routeList = routeDetailService.queryByList(list);
            jsonObject.put("favoriteList",routeList);
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Cache-Control", "no-cache");
        resp.getWriter().write(String.valueOf(jsonObject));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
