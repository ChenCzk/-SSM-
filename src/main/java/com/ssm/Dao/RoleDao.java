package com.ssm.Dao;

import com.ssm.Pojo.Role;
import org.springframework.stereotype.Repository;

@Repository  // 注明是Mapper，采用mapper接口编程
public interface RoleDao {
    Role select(int id);
    int insertRole(Role role);
}
