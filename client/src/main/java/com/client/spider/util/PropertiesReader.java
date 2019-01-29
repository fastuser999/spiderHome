package com.client.spider.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.mysql.cj.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * properties文件读取类
 * 
 */
public class PropertiesReader {

	private static Logger logger = LoggerFactory.getLogger(PropertiesReader.class);
	private static PropertiesReader theReader = new PropertiesReader("/data/spider/config/config.properties");

	Properties properties = new Properties();// 获取Properties实例

	private PropertiesReader(String fileName) {
		try {
			properties.load(new FileReader(fileName));// 载入输入流

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static PropertiesReader instance() {
		return theReader;
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public int getOrDefault(String key, int defaultValue) {
		return (Integer.parseInt(getOrDefault(key, "" + defaultValue)));
	}
	
	public String getOrDefault(String key, String defaultValue) {
		String value = properties.getProperty(key);
		if (StringUtils.isNullOrEmpty(value)) {
			return defaultValue;
		}
		return (value);
	}
}