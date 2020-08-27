package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.IService;
import com.alibaba.fastjson.JSONObject;

/**
 * Create by lixinye
 */
public class UserService implements IService {
    private UserDao userDao = new UserDao();

    public int insertUser(User user){
        int count = userDao.insertUser(user);
        return count;
    }

    public JSONObject userInfoInuse(User user){
        JSONObject jsonObject = userDao.userInfoInUse(user);
        return jsonObject;
    }

    public JSONObject checkLoginInfo(String username,String password){
        JSONObject jsonObject = userDao.checkLoginInfo(username, password);
        return jsonObject;
    }



    public int deleteUserByEmail(String email){
        int count = userDao.deleteUserByEmail(email);
        return count;
    }
}
