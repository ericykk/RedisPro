package com.eric.redis.test;

import com.eric.redis.service.RedisTemplateService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * description:测试redisTemplate
 * author:Eric
 * Date:16/9/29
 * Time:16:23
 * version 1.0.0
 */
public class RedisTemplateTest extends BaseTest{
    @Autowired
    private RedisTemplateService redisTemplateService;

    @Ignore
    @Test
    public void setTest(){
        for(int i=0; i<10000;i++){
            redisTemplateService.set("redisTemplate"+i,"Hello World!");
        }
    }


    @Test
    public void getTest(){
        for(int i=0; i<10000;i++){
            Object result = redisTemplateService.get("redisTemplate"+i);
            Assert.assertEquals("Hello World!",result);
        }
    }

}
