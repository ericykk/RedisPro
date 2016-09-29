package com.eric.redis.service;

import com.eric.redis.repository.RedisSingleSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * description:单实例redis服务
 * author:Eric
 * Date:16/9/29
 * Time:17:26
 * version 1.0.0
 */
@Service
public class RedisSingleService {

    private Logger logger = LogManager.getLogger(RedisSingleService.class);

    @Autowired
    private RedisSingleSource redisSingleSource;

    /**
     * 设置值
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key,String value){
        boolean isSuccess = false;
        Jedis jedis = redisSingleSource.getRedisClient();
        if(jedis != null){
            try{
                jedis.set(key,value);
                isSuccess = true;
            }catch (Exception e){
                logger.error("set operation fail,"+e.getMessage());
            }
        }
        return isSuccess;
    }

    /**
     * 获取值
     * @param key
     * @return
     */
    public String get(final String key){
        String result = null;
        Jedis jedis = redisSingleSource.getRedisClient();
        if(jedis != null){
            try{
                result = jedis.get(key);
            }catch (Exception e){
                logger.error("get operation fail,"+e.getMessage());
            }
        }
        return result;
    }
}
