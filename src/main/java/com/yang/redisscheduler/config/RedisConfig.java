package com.yang.redisscheduler.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.net.UnknownHostException;

/**
 * @Author：YangJx
 * @Description：
 * @DateTime：2018/3/26 10:14
 */
@EnableCaching
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * 键生成器
     *
     * @return
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (Object target, Method method, Object... params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    /**
     * Object类型模板
     *
     * @param redisConnectionFactory
     * @return
     * @throws UnknownHostException
     */
    @Bean
    @ConditionalOnMissingBean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());  //指定key的序列化器
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());  //指定value的序列化工具
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    /**
     * String类型模板
     *
     * @param redisConnectionFactory
     * @return
     * @throws UnknownHostException
     */
    @Bean
    @ConditionalOnMissingBean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        return stringRedisTemplate;
    }
}
