package com.yang.redisscheduler.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author: Yang
 * @date: 2018/9/17 23:16
 * @description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/cacheUser")
    public Object cacheUser(String kk) {
        ValueOperations<String, String> valueOperations = this.stringRedisTemplate.opsForValue();
        valueOperations.set("yang", "杨" + kk, 50L, TimeUnit.SECONDS);
        return "success";
    }

}
