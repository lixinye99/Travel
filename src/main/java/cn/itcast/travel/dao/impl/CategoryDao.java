package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.IDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDao implements IDao {

    public Category querySingle(String cname) {
        /*对象初始化*/
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Category category = null;
        /*操作SQL*/
        try {
            //预编译
            stmt = conn.prepareStatement("select * from tab_category where cname = ?");
            //传参
            stmt.setString(1, cname);
            //执行
            rs = stmt.executeQuery();
            //构建对象
            rs.first();
            category = new Category();
            category.setCid(rs.getInt("cid"));
            category.setCname(cname);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //关闭连接
        JDBCUtils.close(conn, stmt);
        return category;
    }
}
