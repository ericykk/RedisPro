package com.eric.redis.test;

import com.eric.redis.service.RedisSingleService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * description:
 * author:Eric
 * Date:16/9/29
 * Time:17:36
 * version 1.0.0
 */
public class RedisSingleTest extends BaseTest {

    @Autowired
    private RedisSingleService redisSingleService;

    @Ignore
    @Test
    public void setTest(){
        redisSingleService.set("singleService","I am not single!");
    }

    @Test
    public void getTest(){
        String result = redisSingleService.get("singleService");
        Assert.assertEquals("I am not single!",result);
    }
}
