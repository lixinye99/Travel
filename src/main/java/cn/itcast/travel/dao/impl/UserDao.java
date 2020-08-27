package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.IDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import com.alibaba.fastjson.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Create by lixinye
 */
public class UserDao implements IDao {
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    public int insertUser(User user){
        try {
            conn = JDBCUtils.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        int count = 0;
        String sql = "insert into tab_user(username,password,truename,birthday,sex,telephone,email) values(?,?,?,?,?,?,?)";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1,user.getUsername());
            stmt.setString(2,user.getPassword());
            stmt.setString(3,user.getName());
            stmt.setString(4,user.getBirthday());
            stmt.setString(5,user.getSex());
            stmt.setString(6,user.getTelephone());
            stmt.setString(7,user.getEmail());
            count = stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        JDBCUtils.close(conn,stmt);
        return count;
    }

    public JSONObject userInfoInUse(User user){
        try {
            conn = JDBCUtils.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        JSONObject errorinfo = new JSONObject();
        try {
            stmt = conn.prepareStatement("select username,email from tab_user");
            rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("username").equals(user.getUsername())) {
                    errorinfo.put("username", "用户名已经被使用!");
                }
                if (rs.getString("email").equals(user.getEmail())) {
                    errorinfo.put("email", "邮箱已经被人使用!");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        JDBCUtils.close(conn,stmt);
        return errorinfo;
    }

    public JSONObject checkLoginInfo(String username,String password){
        try {
            conn = JDBCUtils.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        JSONObject errorinfo = new JSONObject();
        boolean flag = false;
        try {
            stmt = conn.prepareStatement("select * from tab_user");
            rs = stmt.executeQuery();
            while(rs.next()){
                if(rs.getString("username").equals(username)){
                    flag = true;
                    if(rs.getString("password").equals(password)){
                        errorinfo.put("status","1");
                        errorinfo.put("uid",rs.getString("uid"));
                        return errorinfo;
                    }
                    errorinfo.put("password","密码输入不正确!");
                }
            }
            if(!flag){
                errorinfo.put("username","用户名不正确!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        JDBCUtils.close(conn,stmt);
        return errorinfo;
    }


    public int deleteUserByEmail(String email) {
        int count = 0;
        try {
            conn = JDBCUtils.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            stmt = conn.prepareStatement("delete from tab_user where email='"+ email +"'");
            count = stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return count;

    }
}
