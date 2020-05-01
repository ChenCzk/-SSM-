package com.ssm.Service.Impl;

import com.ssm.Dao.CategoryMapper;
import com.ssm.Pojo.Category;
import com.ssm.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 *
 * */
@Service
public class CategoryServiceImp implements CategoryService {
    @Autowired
    private CategoryMapper mapper;
    @Autowired
    private RedisTemplate redisTemplate;

    Set<ZSetOperations.TypedTuple> set1 = new HashSet<>();

    public List<Category> category_list() {

        List<Category> list = null;

        try {

            SessionCallback sessionCallback = new SessionCallback() {
                @Override
                public Object execute(RedisOperations ops) throws DataAccessException {
                    Set<ZSetOperations.TypedTuple> category_redis = ops.opsForZSet().rangeWithScores("Category", 0, -1);
                    return category_redis;
                }
            };
            Set<ZSetOperations.TypedTuple> category_redis = (Set<ZSetOperations.TypedTuple>) redisTemplate.execute(sessionCallback);

            // redis��keyΪCategory�ֶ�
            if (category_redis == null || category_redis.size() == 0) {
                list = mapper.category_list();
                for (Category cc : list) {
                    ZSetOperations.TypedTuple typedTuple = new DefaultTypedTuple(cc.getName(), Double.valueOf(cc.getId()));
                    set1.add(typedTuple);
                    redisTemplate.opsForZSet().add("Category", set1);
                }

            } else {
                list = new ArrayList<>();
                for (ZSetOperations.TypedTuple s : category_redis) {
                    Category cat = new Category();
                    cat.setId(s.getScore().intValue());
                    cat.setName((String) s.getValue());
                    list.add(cat);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return list;
    }
}
