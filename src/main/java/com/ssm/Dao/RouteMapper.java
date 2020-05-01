package com.ssm.Dao;

import com.ssm.Pojo.Route;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteMapper {

    // 分类栏
    List<Route> findRoute(int cid, int start, int limit); //分页查询
    int count(int cid);//查询cid对应的总记录数

    // 搜索栏
    List<Route> searchRoute(@Param("rname") String rname, @Param("start") int start, @Param("limit") int limit);  //搜索框查询
    int countSearch(@Param("rname") String rname);//查询rname对应的总记录数

    // 查看详情
    Route findRouteByRid(@Param("rid") int rid);

}
