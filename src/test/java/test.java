import com.ssm.Config.SpringConfig;
import com.ssm.Dao.FavoriteMapper;
import com.ssm.Dao.RouteImgMapper;
import com.ssm.Dao.UserMapper;
import com.ssm.Pojo.Role;
import com.ssm.Pojo.Route;
import com.ssm.Pojo.RouteImg;
import com.ssm.Pojo.User;
import com.ssm.Service.Insert;
import com.ssm.Service.Select;
import com.ssm.Service.routeService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
public class test {
    @Test
    public void  test1(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        Insert bean = context.getBean(Insert.class);
        int i = bean.insertRole(new Role(6,"a","dsa"));
        System.out.println(i);
    }
    /*
    * 测试RoleDao
    * */
    @Test
    public void  test2(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        Select bean = context.getBean(Select.class);
        System.out.println(bean.getRole(1));
    }
    /*
    * 测试setuser
    * */
    @Test
    public void test3(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        UserMapper bean = context.getBean(UserMapper.class);
        User user = new User();
        user.setUserName("asda");
        user.setName("asd");
        user.setPassword("a");
        int i = bean.setUser(user);
        System.out.println(i);
    }

    /*
    * 测试Redis
    * */
    @Test
    public void test4(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        RedisTemplate redisTemplate = context.getBean(RedisTemplate.class);
        JedisPoolConfig bean = context.getBean(JedisPoolConfig.class);
        System.out.println(bean.getMaxIdle());

        SessionCallback sessionCallback = new SessionCallback() {
            @Override
            public Object execute(RedisOperations ops) throws DataAccessException {
                Set<ZSetOperations.TypedTuple> category_redis = ops.opsForZSet().rangeWithScores("Category", 0, -1);
                return category_redis;
            }
        };
        Set<ZSetOperations.TypedTuple> category_redis = (Set<ZSetOperations.TypedTuple>) redisTemplate.execute(sessionCallback);
        System.out.println(category_redis);
    }
    @Test
    public void test5(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        routeService bean = context.getBean(routeService.class);
        List<Route> route = bean.findRoute(5, 0, 10);
        System.out.println(route);
    }
    @Test
    public void test6(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        routeService bean = context.getBean(routeService.class);
        List<Route> route = bean.searchRoute("%秒杀%", 0, 10);

        int i = bean.countSearch("a");
        System.out.println(i);
        for (Route route1 : route) {
            System.out.println(route1);
        }
    }
    private static Logger logger = Logger.getLogger(Test.class);

    @Test
    public void test_log4j(){
        // 记录debug级别的信息
            logger.debug("This is debug message.");
        // 记录info级别的信息
        logger.info("This is info message.");
        // 记录error级别的信息
        logger.error("This is error message.");
    }
    @Test
    public void test7(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        routeService bean = context.getBean(routeService.class);
        Route routeByRid = bean.findRouteByRid(1);
        System.out.println(routeByRid);

    }
    @Test
    public void test8(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        RouteImgMapper bean = context.getBean(RouteImgMapper.class);
        List<RouteImg> routeImgs = bean.RouteImgMapper(1);
        System.out.println(routeImgs);
    }
    @Test
    public void test9(){
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        FavoriteMapper bean = context.getBean(FavoriteMapper.class);
        int byURId = bean.findByURId(1, 1);
        System.out.println(byURId);
    }
}
