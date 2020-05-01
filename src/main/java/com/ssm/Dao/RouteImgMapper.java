package com.ssm.Dao;

import com.ssm.Pojo.RouteImg;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RouteImgMapper {
    List<RouteImg> RouteImgMapper (int rid);

}
