package com.ssm.Service;

import com.ssm.Pojo.Route;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface routeService {

    public List<Route> findRoute(int cid, int start, int limit);
    int count(int cid);//查询cid对应的总记录数
    List<Route> searchRoute(String rname,int start,int limit);  //搜索框查询
    int countSearch(String rname);//查询rname对应的总记录数
    Route findRouteByRid(@Param("rid") int rid);

}
