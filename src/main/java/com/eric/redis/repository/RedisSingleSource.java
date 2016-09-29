package com.eric.redis.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * description:单实例redis资源管理
 * author:Eric
 * Date:16/9/29
 * Time:17:20
 * version 1.0.0
 */
@Repository
public class RedisSingleSource{

    private Logger logger = LogManager.getLogger(RedisSingleSource.class);

    @Autowired
    private JedisPool jedisPool;

    /**
     * 获取jedis服务
     * @return
     */
    public Jedis getRedisClient() {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
        }catch (Exception e){
            logger.error("get jedis source fail,"+e.getMessage());
        }
        return jedis;
    }

    /**
     * 回收jedis资源
     * @param jedis
     */
    public void returnResource(Jedis jedis) {
        jedis.close();
    }
}
