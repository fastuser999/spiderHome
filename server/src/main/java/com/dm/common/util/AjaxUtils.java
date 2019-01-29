package com.dm.common.util;

import net.sf.json.JSONObject;

public class AjaxUtils {
    public static JSONObject getErrorObject(String message) {
    	return getObject(0, message, "");
    }
    
    public static JSONObject getErrorObject() {
    	return getErrorObject("服务器繁忙，请稍后再试！");
    }
    
    public static JSONObject getSucceedObject() {
    	return getObject(1, "", "");
    }
    
    public static JSONObject getErrorObject(String message, String url) {
    	return getObject(0, message, url);
    }
    
    public static JSONObject getSucceedObject(String message, String url) {
    	return getObject(1, message, url);
    }
    
    public static JSONObject getObject(int result, String message, String url) {
    	JSONObject jsonObject = new JSONObject();
    	jsonObject.accumulate("result", result);
    	jsonObject.accumulate("message", message);
    	jsonObject.accumulate("url", url);
    	return jsonObject;
    }
}
