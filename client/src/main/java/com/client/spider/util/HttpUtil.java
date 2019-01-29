package com.client.spider.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by user on 2019/1/11.
 */
public class HttpUtil {
	
	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	public static JSONObject httpGetJson(String url) throws Exception {
		// get请求返回结果
		JSONObject jsonResult = null;
		HttpClient client = HttpClientBuilder.create().build();
		;
		// 发送get请求
		HttpGet request = new HttpGet(url);
		HttpResponse response = client.execute(request);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			/** 读取服务器返回过来的json字符串数据 **/
			String strResult = EntityUtils.toString(response.getEntity());
			/** 把json字符串转换成json对象 **/
			jsonResult = JSONObject.parseObject(strResult);
		} else {
			logger.error("get请求提交失败:" + url);
			throw new Exception("http status code:" + response.getStatusLine().getStatusCode());
		}
		return jsonResult;
	}
}
