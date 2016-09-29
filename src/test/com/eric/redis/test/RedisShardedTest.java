package com.eric.redis.test;

import com.eric.redis.service.RedisShardedService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * description:
 * author:Eric
 * Date:16/9/29
 * Time:17:10
 * version 1.0.0
 */
public class RedisShardedTest extends BaseTest {

    @Autowired
    private RedisShardedService redisShardedService;

    @Ignore
    @Test
    public  void setTest(){
        for(int i=0;i<1000;i++){
            redisShardedService.set("shardedRedis"+i,"shardedRedis"+i);
        }
    }


    @Test
    public void getTest(){
        for(int i=0;i<1000;i++){
            String value = redisShardedService.get("shardedRedis"+i);
            System.out.println(value);
        }
    }

}
