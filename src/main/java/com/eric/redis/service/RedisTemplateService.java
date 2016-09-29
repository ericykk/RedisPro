package com.eric.redis.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * description:redis操作模板服务
 * author:Eric
 * Date:16/9/29
 * Time:15:39
 * version 1.0.0
 */
@Service
public class RedisTemplateService {

    private Logger logger = LogManager.getLogger(RedisTemplateService.class);

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 添加数据
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key,Object value){
        boolean isSuccess = false;
        try{
            ValueOperations valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key,value);
            isSuccess = true;
        }catch (Exception e){
            logger.error("set operation fail,"+e.getMessage());
        }
        return isSuccess;
    }

    /**
     * 添加数据并设置过期时间
     * @param key
     * @param value
     * @param expireTime 过期时间 单位秒
     * @return
     */
    public boolean set(final String key,Object value,long expireTime){
        boolean isSuccess = false;
        try{
            ValueOperations valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key,value,expireTime, TimeUnit.SECONDS);
            isSuccess = true;
        }catch (Exception e){
            logger.error("set operation fail,"+e.getMessage());
        }
        return isSuccess;
    }

    /**
     * 获取指定value
     * @param key
     * @return
     */
    public Object get(final String key){
        Object result = null;
        try{
            ValueOperations valueOperations = redisTemplate.opsForValue();
            result = valueOperations.get(key);
        }catch (Exception e){
            logger.error("get operation fail,"+e.getMessage());
        }
        return result;
    }

    /**
     * 判断是否已经存入key
     * @param key
     * @return
     */
    public boolean hasKey(final String key){
        boolean hasKey = false;
        try{
            hasKey = redisTemplate.hasKey(key);
        }catch (Exception e){
            logger.error("hasKey operation fail,"+e.getMessage());
        }
        return hasKey;
    }

}
