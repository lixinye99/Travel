package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.IDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Arno Clare
 */
public class FavoriteDao implements IDao {

    /**
     * @return 返回 SQL 影响行数
     */
    public int insertSingle(Favorite favorite) {
        /*对象初始化*/
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        PreparedStatement stmt = null;
        int res = 0;
        /*操作SQL*/
        try {
            //预编译
            stmt = conn.prepareStatement("insert into tab_favorite values(?, ?, ?)");
            //传参
            stmt.setInt(1, favorite.getRoute().getRid());
            stmt.setString(2, favorite.getDate());
            stmt.setInt(3, favorite.getUser().getUid());
            //执行
            res = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //关闭连接
        JDBCUtils.close(conn, stmt);
        return res;
    }

    public int deleteSingle(Favorite favorite) {
        /*对象初始化*/
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        PreparedStatement stmt = null;
        int res = 0;
        /*操作SQL*/
        try {
            //预编译
            stmt = conn.prepareStatement("delete from tab_favorite where rid = ? and uid = ?");
            //传参
            stmt.setInt(1, favorite.getRoute().getRid());
            stmt.setInt(2, favorite.getUser().getUid());
            //执行
            res = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //关闭连接
        JDBCUtils.close(conn, stmt);
        return res;
    }

    public Favorite querySingle(int rid, int uid) {
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
        Favorite favorite = null;
        /*操作SQL*/
        try {
            //预编译
            stmt = conn.prepareStatement("select * from tab_favorite where rid = ? and uid = ?");
            //传参
            stmt.setInt(1, rid);
            stmt.setInt(2, uid);
            //执行
            rs = stmt.executeQuery();
            //构建对象
            if (rs.next()) {
                Route route = new Route();
                route.setRid(rs.getInt("rid"));
                User user = new User();
                user.setUid(rs.getInt("uid"));
                favorite = new Favorite();
                favorite.setRoute(route);
                favorite.setUser(user);
                favorite.setDate(rs.getString("date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //关闭连接
        JDBCUtils.close(conn, stmt);
        return favorite;
    }

    /**
     * @param columnName 作为查询条件的列名
     * @param data       作为查询条件的值
     * @return           -1 为异常
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
            stmt = conn.prepareStatement("select count(*) from tab_favorite where " + columnName + " = ?");
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



    public int queryCountByUid(int uid){
        int count = 0;
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            //预编译
            stmt = conn.prepareStatement("select * from tab_favorite where uid ='"+ uid + "'");
            rs = stmt.executeQuery();
            while (rs.next()){
                count++;
            }
            //传参
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JDBCUtils.close(conn, stmt);
        return count;
    }

    public List<Integer> queryByUidAndPageNum(int uid,int pageNum){
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Integer> list = new ArrayList<>();

        /*
        第一页：0-11
        第二页：12-23
        * */
        int offset = (pageNum-1)*12;
        try {
            //预编译
            stmt = conn.prepareStatement("select * from tab_favorite where uid ='"+ uid + "' limit 12 offset "+ offset);
            rs = stmt.executeQuery();
            while (rs.next()){
                list.add(Integer.valueOf(rs.getString("rid")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JDBCUtils.close(conn, stmt);
        return list;
    }
}
