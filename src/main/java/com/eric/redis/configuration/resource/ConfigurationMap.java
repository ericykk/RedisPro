package com.eric.redis.configuration.resource;

import com.eric.redis.common.util.IpUtils;
import org.apache.commons.collections.MapUtils;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 全局配置文件
 */
public class ConfigurationMap {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationMap.class);

	private static final String EVN_FILE = "global.properties";
	
    private static final Pattern pattern = Pattern.compile("#\\{(.*?)\\}");

	private static Map<String, String> resourceMap = new HashMap<>();

	private static String evnDir = "";

	static {
		resourceMap = init();
	}

	private ConfigurationMap(){}
	
	/**
	 * 查询属性值
	 * @param key
	 * @return
	 */
	public static String get(String key){
		return resourceMap.get(key);
	}

	public static int getIntValue(String key){
		return MapUtils.getIntValue(resourceMap, key);
	}

	/**
	 * 重新加载配置
	 */
	public synchronized static void flush(){
		resourceMap = init();
	}

	/**
	 * 获的运行环境
	 * @return
	 */
	public static String getEvnDir() {
		return evnDir;
	}

	/**
	 * 查询所有的属性
	 * @return
	 */
	public static Map<String, String> findAllMap() {
		return resourceMap;
	}

	/**
	 * 更新资源文件
	 * @param key
	 * @param value
	 */
	public static void update(String key, String value) {
		Assert.assertNotNull(key);
		if (resourceMap.get(key) == null || resourceMap.get(key).equals(value)) {
			return;
		}

		resourceMap.put(key, value);
	}

	/**
	 * 编译自定义的变量
	 * 语法为:#{}
	 * @param initMap
	 */
	public static void compileCustomVar(Map<String, String> initMap) {
		for (Map.Entry<String, String> entry : initMap.entrySet()) {
			String value = entry.getValue();
			Matcher matcher = pattern.matcher(value);

			if (matcher.find()) {
				String varStr = matcher.group(1);
				String varValue = value.replaceAll("#\\{" + varStr + "\\}", initMap.get(varStr));
				initMap.put(entry.getKey(), varValue);
			}
		}
	}

	/**
	 * 初始化配置
	 * @return
	 */
	private static synchronized Map<String, String> init() {
		Map<String, String> initMap = new HashMap<>();
		evnDir = getProperties(EVN_FILE).getProperty("env");
		forcedUpdateEvnDir();
		List<File> configFiles = getAllPropertiesFile("common");//获取公有配置文件
		List<File> envFiles = getAllPropertiesFile(evnDir);
		if (envFiles.size() > 0) {
			configFiles.addAll(envFiles);
		}

		for (File file : configFiles) {
			if (file.isFile() && file.getName().startsWith("log4j")) {
				continue;
			}

			Properties prop = getProperties(file);
			propTransIntoMap(initMap, prop);
		}

		compileCustomVar(initMap);

		return initMap;
	}

	/**
	 * 强制更新环境
	 */
	private static void forcedUpdateEvnDir() {
		String production_ip = getProperties(EVN_FILE).getProperty("production_ip");
		String ip = IpUtils.getLocalIP();
		if (production_ip.contains(ip)) {
			evnDir = "production";
		}
	}
	
	/**
     * 将Properties转成Map对象
     *
	 * @param initMap
	 * @param prop
	 */
	private static void propTransIntoMap(Map<String, String> initMap, Properties prop) {
		Set<Object> keySet = prop.keySet();
		for (Object key : keySet) {
			String propKey = ((String) key).trim();
			String propValue = prop.getProperty((String) key).trim();
			initMap.put(propKey, propValue);
		}
	}

	/**
	 * 返回资源目录下所有文件  默认后缀为.properties
	 * @param dir
	 * @return
	 */
	private static List<File> getAllPropertiesFile(String dir) {
		return getAllPropertiesFile(dir, ".properties");
	}
	
	
	/**
	 * 返回资源目录dir中所有属性文件
	 * @param dir
	 * @return
	 */
	private static List<File> getAllPropertiesFile(String dir, String endStr) {
		List<File> list = new ArrayList<>();
		File file = new File(ConfigurationMap.class.getClassLoader().getResource(dir).getPath());
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				if (f.getName().endsWith(endStr)) {
					list.add(f);
				}
			}
		}
		return list;
	}


	private static Properties getProperties(String filename) {
		File file = new File(ConfigurationMap.class.getClassLoader().getResource(filename).getPath());
		return getProperties(file);
	}


	private static Properties getProperties(File file) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			LOGGER.error("properties file not found");
		} catch (IOException e) {
			LOGGER.error("properties file IO exception");
		}
		return properties;
	}

	/**
	 * 获取项目路径
	 * @return
	 */
	public static String getClassRootPath() {
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		return path.replaceAll("%20", " ");
	}

}
