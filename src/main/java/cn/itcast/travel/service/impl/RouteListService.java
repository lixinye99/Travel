package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.CategoryDao;
import cn.itcast.travel.dao.impl.RouteDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.IService;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author Arno Clare
 */
public class RouteListService implements IService {
    private final RouteDao routeDao;
    private final CategoryDao categoryDao;

    public RouteListService() {
        routeDao = new RouteDao();
        categoryDao = new CategoryDao();
    }

    public JSONObject service(String condition, String data, int pageIndex, int pageSize) {
        List<Route> routeList;
        int routeCount;
        if (condition.equals("category")) {
            Category category = categoryDao.querySingle(data);
            routeList = getRouteByCid(category.getCid(), pageIndex, pageSize);
            routeCount = getRouteCountByCid(category.getCid());
        } else if (condition.equals("name")) {
            routeList = getRouteByRname(data, pageIndex, pageSize);
            routeCount = getRouteCountByRname(data);
        } else {
            return null;
        }
        //构建 JSONObject
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("routeList", routeList);
        jsonObject.put("routeCount", routeCount);
        return jsonObject;
    }

    /**
     * @param pageIndex 从 1 开始
     */
    private List<Route> getRouteByCid(int cid, int pageIndex, int pageSize) {
        List<Route> routeList = routeDao
                .queryList("cid", cid + "", (pageIndex - 1) * pageSize, pageSize);
        return routeList;
    }

    /**
     * @param pageIndex 从 1 开始
     */
    private List<Route> getRouteByRname(String rname, int pageIndex, int pageSize) {
        List<Route> routeList = routeDao
                .queryListFuzzy("rname", rname, (pageIndex - 1) * pageSize, pageSize);
        return routeList;
    }

    private int getRouteCountByCid(int cid) {
        return routeDao.queryCount("cid", cid + "");
    }

    private int getRouteCountByRname(String rname) {
        return routeDao.queryCountFuzzy("rname", rname);
    }
}
