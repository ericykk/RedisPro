package com.eric.redis.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * description:redis分布式连接池资源服务
 * author:Eric
 * Date:16/9/27
 * Time:15:25
 * version 1.0.0
 */
@Repository
public class RedisShardedSource implements  RedisDataSource{

    private Logger logger = LogManager.getLogger(RedisShardedSource.class);

    @Autowired
    private ShardedJedisPool shardedJedisPool;


    /**
     * 获取分片redis服务
     * 如果连接池中有一台redis服务宕机 则无法获取服务
     * @return
     */
    @Override
    public ShardedJedis getRedisClient() {
        ShardedJedis shardedJedis = null;
        try{
            shardedJedis = shardedJedisPool.getResource();
            return shardedJedis;
        }catch (Exception e){
            logger.error("get redis resource fail,the reason is "+e.getMessage());
            if(shardedJedis != null){
                shardedJedis.close();
            }
        }
        return shardedJedis;
    }

    @Override
    public void returnResource(ShardedJedis shardedJedis) {
            shardedJedis.close();
    }

}
