package com.eric.redis.service;

import com.alibaba.fastjson.JSON;
import com.eric.redis.repository.RedisDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;

/**
 * description:redis分片服务
 * author:Eric
 * Date:16/9/23
 * Time:18:20
 * version 1.0.0
 */
@Service
public class RedisShardedService {

    private Logger logger = LogManager.getLogger(RedisShardedService.class);

    @Autowired
    private RedisDataSource redisDataSource;


    /**
     * 设置值
     * @param key
     * @param value
     * @return
     */
    public String set(String key,Object value){
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        if(shardedJedis != null){
            try{
                String jsonValue = JSON.toJSONString(value);
                result = shardedJedis.set(key, jsonValue);
            }catch (Exception e){
                logger.error("set operation fail[key:"+key+",value:"+value+"]",e);
            }finally {
                shardedJedis.close();
            }
        }
        return result;
    }

    /**
     * 设置值并加上过期时间
     * @param key
     * @param value
     * @param expireTime 过期时间  单位秒
     * @return
     */
    public String set(String key,Object value,int expireTime){
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        if(shardedJedis != null){
            try{
                String jsonValue = JSON.toJSONString(value);
                result = shardedJedis.set(key,jsonValue);
                shardedJedis.expire(key,expireTime);
            }catch (Exception e){
                logger.error("set operation fail[key:"+key+",value:"+value+",expireTime:"+expireTime+"]",e);
            }

        }
        return result;
    }

    /**
     * 根据key获取value
     * @param key
     * @return
     */
    public String get(String key){
        String result = null;
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        if(shardedJedis != null){
            try{
                result = shardedJedis.get(key);
            }catch (Exception e){
                logger.error("get value fail",e);
            }finally {
                shardedJedis.close();
            }
        }
        return result;
    }


    /**
     * 判断是否包含key
     * @param key
     * @return
     */
    public boolean hasKey(String key){
        boolean hasKey = false;
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        if(shardedJedis != null){
            try{
                hasKey = shardedJedis.exists(key);
            }catch (Exception e){
                logger.error("hasKey operation fail",e);
            }finally {
                shardedJedis.close();
            }
        }
        return hasKey;
    }

    /**
     * 删除key对应的数据
     * @param key
     * @return
     */
    public boolean del(String key){
        boolean result = false;
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        if(shardedJedis != null){
            try{
                shardedJedis.del(key);
                result = true;
            }catch (Exception e){
                logger.error("del key operation fail[key:"+key+"]",e);
            }finally {
                shardedJedis.close();
            }
        }
        return result;
    }

    /**
     * 根据key和clazz类型信息获取值
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(String key,Class<T> clazz){
        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
        if(shardedJedis != null){
            try{
                String value = shardedJedis.get(key);
                return JSON.parseObject(value,clazz);
            }catch (Exception e){
                logger.error("get value fail",e);
            }finally {
                shardedJedis.close();
            }
        }
        return null;
    }

}
