package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.IService;
import com.alibaba.fastjson.JSONObject;

import javax.jws.WebService;
import java.time.LocalDate;
import java.util.List;

public class FavoriteService implements IService {
    private final FavoriteDao favoriteDao;

    public FavoriteService() {
        favoriteDao = new FavoriteDao();
    }

    public JSONObject service(int rid, int uid) {
        JSONObject jsonObject = new JSONObject();
        Favorite favorite = favoriteDao.querySingle(rid, uid);
        if (favorite == null) {
            favorite = new Favorite();
            Route route = new Route();
            route.setRid(rid);
            favorite.setRoute(route);
            User user = new User();
            user.setUid(uid);
            favorite.setUser(user);
            favorite.setDate(LocalDate.now().toString());
            addFavorite(favorite);
            jsonObject.put("msg", "收藏成功");
        } else {
            removeFavorite(favorite);
            jsonObject.put("msg", "取消收藏");
        }
        int count = favoriteDao.queryCount("rid", rid + "");
        jsonObject.put("count", count);
        return jsonObject;
    }

    public void addFavorite(Favorite favorite) {
        favoriteDao.insertSingle(favorite);
    }

    public void removeFavorite(Favorite favorite) {
        favoriteDao.deleteSingle(favorite);
    }

    public int queryCountOfRoute(int rid) {
        return favoriteDao.queryCount("rid", rid + "");
    }


    public int queryPageCount(int uid){
        int count = favoriteDao.queryCountByUid(uid);
        return (count/12)+1;
    }

    public List<Integer> queryByUidAndPageNum(int uid,int pageNum){
        List<Integer> list = favoriteDao.queryByUidAndPageNum(uid, pageNum);
        return list;
    }
}
