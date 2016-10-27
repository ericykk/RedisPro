package com.eric.redis.configuration.resource;
import com.eric.redis.common.util.IpUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * 应用的配置文件
 */
public class AppPropertyConfigurer extends PropertyPlaceholderConfigurer {

    private static final Logger LOGGER = LogManager.getLogger(AppPropertyConfigurer.class);

    @Override
    protected Properties mergeProperties() throws IOException {
    	Properties superProps = super.mergeProperties();
        superProps.put("env", ConfigurationMap.getEvnDir());
        //初始化日志
        File file = new File(ConfigurationMap.getClassRootPath() + superProps.getProperty("env") + "/log4j2.xml");
        LoggerContext context =(LoggerContext)LogManager.getContext(false);
        context.setConfigLocation(file.toURI());
        LOGGER.info(">>>>> 运行环境：" + superProps.getProperty("env"));
        LOGGER.info(">>>>> 服务器IP：" + IpUtils.getLocalIP());
        return superProps;
    }
}
