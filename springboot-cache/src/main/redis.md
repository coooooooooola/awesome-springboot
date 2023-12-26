### 1. RedisTemplate
五种数据结构的操作
```
redisTemplate.opsForValue(); //操作字符串
redisTemplate.opsForHash(); //操作hash
redisTemplate.opsForList(); //操作list
redisTemplate.opsForSet(); //操作set
redisTemplate.opsForZSet(); //操作有序zset
```
也可以使用自定义redisTemplate，主要就是分别设置上面几种数据类型的序列化属性，比如
```java
@Configuration
public class RedisConfig{
    @Bean
    public RedisTemplate<String, Object>redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
        // Json序列化配置
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // String 的序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
```

### 2. Redisson
#### 2.1 Redisson和Jedis、Lettuce有什么区别？
Redisson和它俩的区别就像一个用鼠标操作图形化界面，一个用命令行操作文件。Redisson是更高层的抽象，Jedis和Lettuce是Redis命令的封装。

Jedis是Redis官方推出的用于通过Java连接Redis客户端的一个工具包，提供了Redis的各种命令支持
Lettuce是一种可扩展的线程安全的 Redis 客户端，通讯框架基于Netty，支持高级的 Redis 特性，比如哨兵，集群，管道，自动重新连接和Redis数据模型。 Spring Boot 2.x 开始 Lettuce 已取代 Jedis 成为首选 Redis 的客户端。
Redisson是架设在Redis基础上，通讯基于Netty的综合的、新型的中间件，企业级开发中使用Redis的最佳范本

Jedis把Redis命令封装好，Lettuce则进一步有了更丰富的Api，也支持集群等模式。但是两者也都点到为止，只给了你操作Redis数据库的脚手架，而Redisson则是基于Redis、Lua和Netty建立起了成熟的分布式解决方案，甚至redis官方都推荐的一种工具集。



### 3. 分布式锁的实现方式
#### 3.1 redis基础命令实现 （setIfAbsent加锁，get+del解锁）
```
 // 加锁
 public Boolean tryLock(String key, String value, long timeout, TimeUnit unit) {
 return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit);
 }
 
 // 解锁，防止删错别人的锁，以uuid为value校验是否自己的锁
 public void unlock(String lockName, String uuid) {
 if(uuid.equals(redisTemplate.opsForValue().get(lockName)){        redisTemplate.opsForValue().del(lockName);    }
}

 //应用伪代码
 if (tryLock()) {
     // code here
 } finally {
    unlock();
 }
```
缺点： 解锁get del不是原子的，高并发下无法保证线程安全


#### 3.2 LUA脚本实现
> Lua脚本是redis已经内置的一种轻量小巧语言，其执行是通过redis的eval/evalsha命令来运行，把操作封装成一个Lua脚本，如论如何都是一次执行的原子操作。

