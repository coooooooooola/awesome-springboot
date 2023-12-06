package com.zeburan.springbootcache.controller;

import com.alibaba.fastjson.JSONObject;
import com.zeburan.springbootcache.utils.RedisUtil;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("redis")
@Slf4j
public class RedisController {

//    private final RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

//    public RedisController(RedisTemplate redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//    @GetMapping("save")
//    public JSONObject save(String key, String value) {
//        redisTemplate.opsForValue().set(key, value);
//        return new JSONObject();
//    }

    @GetMapping("get")
    public JSONObject get(@Param("key") String key) {
//        redisUtil.set("11","223334234234");
        Object obj = redisUtil.get(key);
        log.info("get info {}", obj);

        RedisURI redisUri = RedisURI.builder()
                .withHost("127.0.0.1")
                .withPort(6379)
                .withPassword("")
                .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();
        RedisClient redisClient = RedisClient.create(redisUri);
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> commands = connection.sync();
        log.info(commands.get(key));
        return new JSONObject();
    }

    @GetMapping("set")
    public JSONObject set(String key) {
        Jedis jedis = new Jedis("localhost", 6379);
        log.info(jedis.get(key));
        return new JSONObject();
    }


}
//查不到redis数据：https://blog.csdn.net/xjszsd/article/details/121746176