package com.zeburan.springbootcache.controller;

/**
 * @Author：zeburan
 * @Date：2023/12/7 14:51
 * Function: redis分布式锁的实现方式：
 * 1）
 */
public class RedisDistributedLockController {

    /**
     * 实现方式 1： 使用redis setIfAbsent加锁，redis get+del解锁
     * 实现：
         * // 加锁
         * public Boolean tryLock(String key, String value, long timeout, TimeUnit unit) {
         * return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit);
         * }
         * // 解锁，防止删错别人的锁，以uuid为value校验是否自己的锁
         * public void unlock(String lockName, String uuid) {
         * if(uuid.equals(redisTemplate.opsForValue().get(lockName)){        redisTemplate.opsForValue().del(lockName);    }
         * }
         * <p>
         * // 结构
         * if(tryLock){
         * // todo
         * }finally{
         * unlock;
         * }
     * 缺点：
         * 解锁的get del不是原子的，高并发下无法保证线程安全
     * */
}
