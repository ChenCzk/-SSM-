package com.ssm.Config;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/*
 * Spring配置类，MyBatis配置被接管
 * */
@ComponentScan(basePackages = "com.ssm")
@Import(RedisConfig.class)
@EnableTransactionManagement
public class SpringConfig {

    private DataSource dataSource;

    /*配置数据源*/
    @Bean(name = "dataSource")
    public DataSource initDataSource() {
        if (dataSource != null) {
            return dataSource;
        }
        Properties props = new Properties();
        props.setProperty("driverClassName", "com.mysql.cj.jdbc.Driver");
        props.setProperty("url", "jdbc:mysql://localhost:3306/webdb?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8");
        props.setProperty("username", "root");
        props.setProperty("password", "123");
        props.setProperty("maxActive", "200");
        props.setProperty("maxIdle", "20");
        props.setProperty("maxWait", "30000");
        try {
            dataSource = BasicDataSourceFactory.createDataSource(props);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    /*
     * 获取SqlSessionFactoryBean
     * */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean getSqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        sqlSessionFactoryBean.setTypeAliasesPackage("");  //设置别名包
        sqlSessionFactoryBean.setDataSource(initDataSource());  //配置数据源
        Resource[] resource = {
                new ClassPathResource("Mapper/RoleDao.xml"),
                new ClassPathResource("Mapper/UserMapper.xml"),
                new ClassPathResource("Mapper/CategoryMapper.xml"),
                new ClassPathResource("Mapper/RouteMapper.xml"),
                new ClassPathResource("Mapper/RouteImgMapper.xml"),
                new ClassPathResource("Mapper/SellerMapper.xml"),
                new ClassPathResource("Mapper/FavoriteMapper.xml")

        };
        sqlSessionFactoryBean.setMapperLocations(resource);         //引用mapper

        //或者直接导入MyBatis配置文件
        /*Resource resource = new ClassPathResource("mybatis.xml");
        sqlSessionFactoryBean.setConfigLocation(resource);
        */
        return sqlSessionFactoryBean;
    }

    /*
     *  采用自动扫描，发现Mapper接口,使用接口编程
     * */
    @Bean
    public MapperScannerConfigurer initMapperScannerConfigurer() {
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        //扫描Mapper
        msc.setBasePackage("com.ssm.Dao");
        msc.setSqlSessionFactoryBeanName("sqlSessionFactory");
        //区分注解扫描@Repository才能识别是mapper
        msc.setAnnotationClass(Repository.class);
        return msc;
    }


    /*
     * 实现接口方法，使得返回数据库事务管理器
     * */
    @Bean(name = "transactionManager")
    public PlatformTransactionManager getplatformTransactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(initDataSource());
        return transactionManager;
    }

}

