package com.zeburan.springbootcache.controller;

import com.zeburan.springbootcache.utils.RedisOperation;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @Author：zeburan
 * @Date：2023/12/7 13:24
 * @Function: redis基础操作
 */
@RestController
@RequestMapping("redis")
@Slf4j
public class RedisOperationController {

//    private final RedisTemplate redisTemplate;

    @Autowired
    private RedisOperation redisOperation;

//    public RedisController(RedisTemplate redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//    @GetMapping("save")
//    public JSONObject save(String key, String value) {
//        redisTemplate.opsForValue().set(key, value);
//        return new JSONObject();
//    }

    /**
     * 使用redisTemplate操作redis，可支持lettuce/Jedis两个客户端。
     * ⭐需要重定义一个RedisTemplateConfig，修改序列化配置，否则会导致乱码https://blog.csdn.net/xjszsd/article/details/121746176
     *
     * @param key
     * @return
     */
    @GetMapping("getone")
    public Object getByRedisTemplate(@Param("key") String key) {
        redisOperation.get(key);
        Object obj = redisOperation.get(key);
        log.info("get info {}", obj);
        return obj;
    }

    /**
     * 使用lettuce客户端操作redis
     *
     * @param key
     * @return
     */
    @GetMapping("gettwo")
    public Object getBylettuce(@Param("key") String key) {
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
        return commands.get(key);
    }

    /**
     * 使用Jedis客户端操作redis
     *
     * @param key
     * @return
     */
    @GetMapping("getthree")
    public Object getByJedis(@Param("key") String key) {
        Jedis jedis = new Jedis("localhost", 6379);
        log.info(jedis.get(key));
        return jedis.get(key);
    }

    /**
     * 使用redisTemplate操作redis，可支持lettuce/Jedis两个客户端,默认lettuce
     *
     * @param key
     * @param value
     */
    @GetMapping("setone")
    public void setByRedisTemplate(@Param("key") String key, @Param("value") String value) {
        redisOperation.set(key, value);
    }

    /**
     * 使用lettuce客户端操作redis
     *
     * @param key
     * @param value
     */
    @GetMapping("settwo")
    public void setBylettuce(@Param("key") String key, @Param("value") String value) {
        RedisURI redisUri = RedisURI.builder()
                .withHost("127.0.0.1")
                .withPort(6379)
                .withPassword("")
                .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();
        RedisClient redisClient = RedisClient.create(redisUri);
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> commands = connection.sync();
        commands.set(key, value);
    }

    /**
     * 使用Jedis客户端操作redis(写操作)
     *
     * @param key
     * @param value
     */
    @GetMapping("setthree")
    public void setByJedis(@Param("key") String key, @Param("value") String value) {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.set(key, value);
    }

    /**
     * List类型操作
     */
    @GetMapping("leftPush")
    public void leftPush() {
        redisOperation.leftPush("listkey", "abc");
        redisOperation.leftPush("listkey", "efg");
    }

}