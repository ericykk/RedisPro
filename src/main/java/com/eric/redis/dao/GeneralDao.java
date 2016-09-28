package com.eric.redis.dao;

import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 通用dao
 *
 * @author eric
 * @create 2016-09-08 22:35
 **/
@Repository
public interface GeneralDao {

    Date getGeneralDate();

}
