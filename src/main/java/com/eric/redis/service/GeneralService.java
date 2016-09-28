package com.eric.redis.service;

import com.eric.redis.dao.GeneralDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 通用service
 *
 * @author eric
 * @create 2016-09-08 22:37
 **/
@Service
public class GeneralService {
    @Autowired
    private GeneralDao generalDao;

    /**
     * 获取数据库当前时间
     * @return
     */
    public Date getGeneralDate(){
        return generalDao.getGeneralDate();
    }
}
