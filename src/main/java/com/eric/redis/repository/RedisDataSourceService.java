package com.eric.redis.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * description:统一管理redis资源服务
 * author:Eric
 * Date:16/9/27
 * Time:15:25
 * version 1.0.0
 */
@Repository
public class RedisDataSourceService implements  RedisDataSource{

    private Logger logger = LogManager.getLogger(RedisDataSourceService.class);

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    @Override
    public ShardedJedis getRedisClient() {
        ShardedJedis shardedJedis = null;
        try{
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis;
        }catch (Exception e){
            logger.error("get redis resource fail,the reason is"+e.getMessage());
        }
        return shardedJedis;
    }

    @Override
    public void returnResource(ShardedJedis shardedJedis) {
            shardedJedis.close();
    }
}
