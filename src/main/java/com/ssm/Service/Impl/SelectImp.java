package com.ssm.Service.Impl;

import com.ssm.Dao.RoleDao;
import com.ssm.Pojo.Role;
import com.ssm.Service.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SelectImp implements Select {

    @Autowired
    private RoleDao dao;

    public Role getRole(int id) {
        return dao.select(id);
    }
}
