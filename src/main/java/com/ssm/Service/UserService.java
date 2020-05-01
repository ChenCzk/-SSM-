package com.ssm.Service;


import com.ssm.Pojo.User;

public interface UserService {
    User findByuserName(String userName);

    int setUser(User user);

    int updateToStatus(String code);
}
