package com.ssm.Dao;

import com.ssm.Pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("categoryMapper")
public interface CategoryMapper {
    List<Category> category_list();
}
