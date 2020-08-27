package cn.itcast.travel.web.servlet;

import cn.itcast.travel.util.JDBCUtils;

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
@WebServlet("/headListSearch")
public class HeadListSearchServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*连接数据库进行查询并保存到字符串中*/
        String str = "";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs=null;
        try {
            conn = JDBCUtils.getConnection();
            stmt = conn.prepareStatement("select * from tab_category");
            rs = stmt.executeQuery();
            while (rs.next()){
                str += rs.getString("cname") + ",";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.setHeader("Cache-Control", "no-cache");
        JDBCUtils.close(conn,stmt);
        resp.getWriter().write(str);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
