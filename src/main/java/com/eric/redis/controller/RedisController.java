package com.eric.redis.controller;

import com.eric.redis.common.controller.BaseController;
import com.eric.redis.common.model.JsonResult;
import com.eric.redis.configuration.annotation.RedisCache;
import com.eric.redis.service.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 通用控制器
 *
 * @author eric
 * @create 2016-09-08 22:39
 **/
@Controller
@RequestMapping(value = "/general")
public class RedisController extends BaseController{

    @Autowired
    private GeneralService generalService;

    /**
     * 获取当前时间
     * @return
     */
    @RedisCache
    @ResponseBody
    @RequestMapping(value = "/getDate",method = RequestMethod.GET)
    public JsonResult getCurrentDate(){
        Date currentDate = generalService.getGeneralDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ok().put("nowDate",currentDate);
    }

}
