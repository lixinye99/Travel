package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.IDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Arno Clare
 */
public class RouteDao implements IDao {

    public Route querySingle(int rid) {
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
        Route route = null;
        /*操作SQL*/
        try {
            //预编译
            stmt = conn.prepareStatement("select * from tab_route where rid = ?");
            //传参
            stmt.setInt(1, rid);
            //执行
            rs = stmt.executeQuery();
            //构建对象
            rs.first();
            route = createRoute(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //关闭连接
        JDBCUtils.close(conn, stmt);
        return route;
    }

    public List<Route> queryList(String columnName, String data) {
        return queryList(columnName, data, 0, 0);
    }

    /**
     * @param columnName    作为查询条件的列名
     * @param data          作为查询条件的值
     * @param offset        从 0 开始，从下一行开始查询
     * @param count         查询行数，-1 为查询到结尾，0 为查询全部
     */
    public List<Route> queryList(String columnName, String data, int offset, int count) {
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
        List<Route> routeList = null;
        /*操作SQL*/
        //创建sql
        String sql = "select * from tab_route where " + columnName + " = " + data;
        if (count != 0) {
            sql += " limit " + offset + ", " + count;
        }
        try {
            //预编译
            stmt = conn.prepareStatement(sql);
            //执行
            rs = stmt.executeQuery();
            //构建List
            routeList = new ArrayList<>();
            while (rs.next()) {
                Route route = createRoute(rs);
                routeList.add(route);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //关闭连接
        JDBCUtils.close(conn, stmt);
        return routeList;
    }

    /**
     * @param columnName    作为查询条件的列名
     * @param data          作为查询条件的值
     * @param offset        从 0 开始，从下一行开始查询
     * @param count         查询行数，-1 为查询到结尾，0 为查询全部
     */
    public List<Route> queryListFuzzy(String columnName, String data, int offset, int count) {
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
        List<Route> routeList = null;
        /*操作SQL*/
        //创建sql
        String sql = "select * from tab_route where " + columnName + " like ?";
        if (count != 0) {
            sql += " limit " + offset + ", " + count;
        }
        try {
            //预编译
            stmt = conn.prepareStatement(sql);
            //传参
//            不能使用此传参，会自动添加单引号导致无法搜索出正确结果
//            stmt.setString(1, columnName);
            stmt.setString(1, "%" + data + "%");
            //执行
            rs = stmt.executeQuery();
            //构建List
            routeList = new ArrayList<>();
            while (rs.next()) {
                Route route = createRoute(rs);
                routeList.add(route);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //关闭连接
        JDBCUtils.close(conn, stmt);
        return routeList;
    }

    /**
     * @param columnName    查询条件的列名
     * @param data          查询条件的值
     * @return              -1 为异常
     */
    public int queryCount(String columnName, String data) {
        /*对象初始化*/
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int count = -1;
        /*操作SQL*/
        try {
            //预编译
            stmt = conn.prepareStatement("select count(*) from tab_route where " + columnName + " = ?");
            //传参
            stmt.setString(1, data);
            //执行
            rs = stmt.executeQuery();
            //提取结果
            rs.first();
            count = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //关闭连接
        JDBCUtils.close(conn, stmt);
        return count;
    }

    /**
     * @param columnName    查询条件的列名
     * @param data          查询条件的值
     * @return              -1 为异常
     */
    public int queryCountFuzzy(String columnName, String data) {
        /*对象初始化*/
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int count = -1;
        /*操作SQL*/
        //创建sql
        String sql = "select count(*) from tab_route where " + columnName + " like ?";
        try {
            //预编译
            stmt = conn.prepareStatement(sql);
            //传参
//            不能使用此传参，会自动添加单引号导致无法搜索出正确结果
//            stmt.setString(1, columnName);
            stmt.setString(1, "%" + data + "%");
            //执行
            rs = stmt.executeQuery();
            //提取结果
            rs.first();
            count = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //关闭连接
        JDBCUtils.close(conn, stmt);
        return count;
    }

    private Route createRoute(ResultSet rs) {
        try {
            Route route = new Route();
            route.setRid(Integer.parseInt(rs.getString("rid")));
            route.setRname(rs.getString("rname"));
            route.setPrice(rs.getDouble("price"));
            route.setRouteIntroduce(rs.getString("routeIntroduce"));
            route.setRflag(rs.getString("rflag"));
            route.setRdate(rs.getString("rdate"));
            route.setIsThemeTour(rs.getString("isThemeTour"));
            route.setCount(rs.getInt("count"));
            route.setCid(rs.getInt("cid"));
            route.setRimage(rs.getString("rimage"));
            route.setSid(rs.getInt("sid"));
            route.setSourceId(rs.getString("sourceId"));
            return route;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Seller queryByRoute(Route route){
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Seller seller = new Seller();
        try {
            //预编译
            stmt = conn.prepareStatement("select * from tab_seller where sid='" + route.getSid() +"'");
            rs = stmt.executeQuery();
            rs.first();
            seller.setSname(rs.getString("sname"));
            seller.setConsphone(rs.getString("consphone"));
            seller.setAddress(rs.getString("address"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seller;
    }

    public List<Route> queryByList(List<Integer> list) {
        List<Route> routeList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Route route = querySingle(list.get(i));
            routeList.add(route);
        }
        return routeList;
    }
}
