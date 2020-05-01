package com.ssm.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {


    //创建连接池
    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig initJedisPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(300);
        poolConfig.setMaxTotal(100);
        poolConfig.setMaxWaitMillis(20000);
        return poolConfig;
    }

    @Bean("jedisConnectionFactory")
    public JedisConnectionFactory initJedisConnectionFactory() {
        //创建连接工厂
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setHostName("127.0.0.1");
        connectionFactory.setPort(6379);
        connectionFactory.setPoolConfig(initJedisPoolConfig());
        //调用后初始化方法，没有他会报异常
        connectionFactory.afterPropertiesSet();
        return connectionFactory;
    }

    @Bean("redisTemplate")
    public RedisTemplate initRedisTemplate() {
        //自定义序列化器
        RedisSerializer jdkSerialization = new JdkSerializationRedisSerializer();
        RedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //定义RedisTemplate
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(initJedisConnectionFactory());
        redisTemplate.setDefaultSerializer(jdkSerialization);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jdkSerialization);


        return redisTemplate;
    }

    /*
     * 属性解析
     * */
    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }


}
