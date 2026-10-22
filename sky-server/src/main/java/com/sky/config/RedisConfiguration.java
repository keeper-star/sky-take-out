package com.sky.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.support.collections.RedisCollectionFactoryBean;


@Configurable
@Slf4j
public class RedisConfiguration {

    //解决redis中文乱码问题
    @Primary
    @Bean
    public RedisTemplate redisTemplate(RedisCollectionFactoryBean redisCollectionFactoryBean){

        RedisTemplate redisTemplate=new RedisTemplate();
        //设置redis的连接工厂
        redisTemplate.setConnectionFactory((RedisConnectionFactory) redisCollectionFactoryBean);
        //设置redis key 的序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }


}
