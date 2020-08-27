package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Create by lixinye
 */
@WebServlet("/indexListSearch")
public class IndexServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*创建json对象进行回传*/
        JSONObject jsonObject = new JSONObject();
        /*连接数据库进行查询并保存到字符串中*/
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs=null;
        try {
            conn = JDBCUtils.getConnection();
            stmt = conn.prepareStatement("select * from tab_route limit 6");
            rs = stmt.executeQuery();
            while (rs.next()){
                Route route = new Route();
                route.setRid(Integer.parseInt(rs.getString("rid")));
                route.setRname(rs.getString("rname"));
                route.setPrice(Double.parseDouble(rs.getString("price")));
                route.setRouteIntroduce(rs.getString("routeIntroduce"));
                route.setRimage(rs.getString("rimage"));
                String beanString = JSONObject.toJSONString(route);
                jsonObject.put("route"+rs.getString("rid"),beanString);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Cache-Control", "no-cache");
        JDBCUtils.close(conn,stmt);
        resp.getWriter().write(String.valueOf(jsonObject));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
