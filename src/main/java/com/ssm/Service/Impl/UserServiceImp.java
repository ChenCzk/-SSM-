package com.ssm.Service.Impl;

import com.ssm.Dao.UserMapper;
import com.ssm.Pojo.User;
import com.ssm.Service.UserService;
import com.ssm.Utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserMapper userMapper = null;

    /*
     * 判断用户名是否重复
     * */
    public User findByuserName(String userName) {
        return userMapper.findByuserName(userName);
    }

    /*
     * 保存用户，并发送文件
     * */
    public int setUser(User user) {
        String code = UUID.randomUUID().toString().replace("-","");
        String content = "<a href='http://localhost:8080/myTravel_war_exploded/reg/email.do?code="+code+"'>点击激活</a>";
        // 设置用户的status，code
        user.setStatus("N");
        user.setCode(code);
        // 发送邮件
        MailUtils.sendMail(user.getEmail(), content, "用户激活");
        return userMapper.setUser(user);

    }

    public int updateToStatus(String code) {
        return userMapper.updateToStatus(code);
    }
}
