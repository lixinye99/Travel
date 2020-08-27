package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.IService;

import java.util.List;

/**
 * Create by lixinye
 */
public class RouteDetailService implements IService {
    private RouteDao routeDao = new RouteDao();

    public Route querySingle(int rid){
        Route route = routeDao.querySingle(rid);
        return route;
    }

    public Seller querysellyByRoute(Route route){
        Seller seller = routeDao.queryByRoute(route);
        return seller;
    }

    public List<Route> queryByList(List<Integer> list){
        List<Route> routes = routeDao.queryByList(list);
        return routes;
    }

}
