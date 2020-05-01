package com.ssm.Dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteMapper {
    int findByURId(@Param("uid") int uid,@Param("rid") int rid);

}
