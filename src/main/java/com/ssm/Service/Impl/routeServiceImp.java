package com.ssm.Service.Impl;

import com.ssm.Config.SpringConfig;
import com.ssm.Dao.RouteMapper;
import com.ssm.Pojo.Route;
import com.ssm.Service.routeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class routeServiceImp implements routeService {
    @Autowired
    RouteMapper mapper;

    @Override
    public List<Route> findRoute(int cid, int start, int limit) {
        List<Route> route = mapper.findRoute(cid, start, limit);

        return route;
    }

    @Override
    public List<Route> searchRoute(String rname, int start, int limit) {
        List<Route> routes = mapper.searchRoute(rname, start, limit);
        return routes;
    }

    @Override
    public int countSearch(String rname) {
        int i = mapper.countSearch(rname);
        return i;
    }

    @Override
    public int count(int cid) {
        return mapper.count(cid);
    }

    @Override
    public Route findRouteByRid(int rid) {
        return mapper.findRouteByRid(rid);
    }
}
