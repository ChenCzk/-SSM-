package com.ssm.Service.Impl;

import com.ssm.Dao.RoleDao;
import com.ssm.Pojo.Role;
import com.ssm.Service.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InsertImp implements Insert {
    @Autowired
    private RoleDao roleDao;

    public int insertRole(Role role) {
        return roleDao.insertRole(role);
    }
}
