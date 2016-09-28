package com.eric.redis.common.controller;
import com.eric.redis.common.model.BaseEntity;
import com.eric.redis.common.model.JsonResult;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * description:基础控制器
 * author:Eric
 * Date:16/9/21
 * Time:18:10
 * version 1.0.0
 */
public class BaseController {

    private static final Logger logger = LogManager.getLogger(BaseController.class);

    protected static final JsonResult OK;
    protected static final JsonResult FAIL;

    public BaseController() {
    }

    public String forward(String page) {
        return "forward:" + page;
    }

    public static JsonResult json(BaseEntity value) {
        return (new JsonResult().put(value));
    }

    public static JsonResult json(String message, BaseEntity value) {
        return ok(message).put(value);
    }

    public JsonResult ok() {
        return ok("");
    }

    public static JsonResult ok(String message) {
        return new JsonResult(JsonResult.STATUS.OK, message);
    }

    public static JsonResult ok(BaseEntity value) {
        return ok("", value);
    }

    public static JsonResult ok(String message, BaseEntity value) {
        return ok(message).put(value);
    }

    public static JsonResult ok(String message, String key, Object value) {
        return (new JsonResult(JsonResult.STATUS.OK, message)).put(key, value);
    }

    public static JsonResult fail() {
        return fail("");
    }

    public static JsonResult fail(String message) {
        return new JsonResult(JsonResult.STATUS.FAIL, message);
    }

    public static JsonResult fail(BaseEntity value) {
        return fail("", value);
    }

    public static JsonResult fail(String key, Object value) {
        return fail("", key, value);
    }

    public static JsonResult fail(String message, String key, Object value) {
        return (new JsonResult(JsonResult.STATUS.FAIL, message)).put(key, value);
    }

    protected String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("Cdn-Src-Ip");
        if(StringUtils.isNotBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }

        if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    static {
        OK = new JsonResult(JsonResult.STATUS.OK);
        FAIL = new JsonResult(JsonResult.STATUS.FAIL);
    }
}
