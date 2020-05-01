package com.ssm.Dao;

import com.ssm.Pojo.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
        User findByuserName(String userName);   // 通过用户名查找对应的字段
        int  setUser(User user);                // 增加用户
        int  updateToStatus(String code);       // 通过code查找用户,并设置该用户激活状态status为Y

}
