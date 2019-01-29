package com.dm.common.util;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dm.admin.entity.Wechatorder;
import com.dm.admin.service.impl.WechatorderServiceImpl; 
  

public class WxPayUtils { 
	
	private static Logger logger = LoggerFactory.getLogger(WxPayUtils.class);

    private WechatorderServiceImpl wechatorderService;
	
	public WxPayUtils(WechatorderServiceImpl wechatorderService) {
		this.wechatorderService = wechatorderService;
	}
	
  public String genPayUrl(int amount) { 
    String appid = "wx25fcc6b24db04ed2"; 
    String mch_id = "1502931871"; 
    String paternerKey = "JsfweNsdfL2mweees5fksdfjNsfsdfW2"; 
      
    String out_trade_no = getTradeNo(); 
    Map<String, String> paraMap = new TreeMap<String, String>(); 
    String nonce_str = create_nonce_str();
    String attach = "信誉查询114";
    String body = "信誉查询114";
    int total_fee = amount;
    String trade_type = "MWEB";
    String notify_url = "http://www.xinyu114.com/pay/wechatPay/notify";
    String scene_info = "'h5_info':{'type':'Wap','wap_url':'http://www.xinyu114.com','wap_name': '信誉查询114'}";

    paraMap.put("appid", appid); 
    paraMap.put("attach", attach); 
    paraMap.put("body", body); 
    paraMap.put("mch_id", mch_id); 
    paraMap.put("nonce_str", nonce_str); 
    paraMap.put("out_trade_no", out_trade_no); 
    paraMap.put("spbill_create_ip", SessionUtils.getIPAddress()); 
    paraMap.put("total_fee", "" + total_fee); 
    paraMap.put("trade_type", trade_type); 
    paraMap.put("notify_url", notify_url); 
    paraMap.put("scene_info", scene_info); 
    String sign = getSign(paraMap, paternerKey);
    // 微信统一下单
    Unifiedorder unifiedorder = new Unifiedorder();
    unifiedorder.setAppid(appid);
    unifiedorder.setMch_id(mch_id);
    unifiedorder.setNonce_str(nonce_str);
    unifiedorder.setSign(sign);
    unifiedorder.setBody(body);
    unifiedorder.setAttach(attach);
    unifiedorder.setOut_trade_no(out_trade_no);
    unifiedorder.setTotal_fee(total_fee);
    unifiedorder.setSpbill_create_ip(SessionUtils.getIPAddress());
    unifiedorder.setNotify_url(notify_url);
    unifiedorder.setTrade_type(trade_type);
    unifiedorder.setScene_info(scene_info);
    
    String xmlInfo = HttpXmlUtils.xmlH5Info(unifiedorder);
    String wxUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    String method = "POST";
    //获取微信返回的结果
    String xml = HttpXmlUtils.httpsRequest(wxUrl, method, xmlInfo).toString();
    
    Map<String, String> msgMap = HttpXmlUtils.xml2Map(xml);
    if (msgMap != null) {
    	if (msgMap.get("return_code").equals("SUCCESS")) {
//    		User user = (User)SessionUtils.getUser();
//    		Date date = new Date();
//    		Wechatorder order = new Wechatorder(out_trade_no, nonce_str, user.getId(), date, date, 0);
//    		wechatorderService.insert(order);
//            return msgMap.get("mweb_url");
        } else {
        	logger.error(msgMap.get("return_msg"));
        }
    }
    
    
    return null;
  }
  
  
  
  private String create_nonce_str() { 
      String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; 
      String res = ""; 
      for (int i = 0; i < 16; i++) { 
        Random rd = new Random(); 
        res += chars.charAt(rd.nextInt(chars.length() - 1)); 
      } 
      return res; 
  } 

  private String getTradeNo(){  
    return UUID.randomUUID().toString().replace("-", "");
  } 
    
  private String getSign(Map<String, String> params, String paternerKey ) { 
	  StringBuffer sb = new StringBuffer();
      Set es = params.entrySet();//所有参与传参的参数按照accsii排序（升序）
      Iterator it = es.iterator();
      while(it.hasNext()) {
          Map.Entry entry = (Map.Entry)it.next();
          String k = (String)entry.getKey();
          Object v = entry.getValue();
          if(null != v && !"".equals(v) 
                  && !"sign".equals(k) && !"key".equals(k)) {
              sb.append(k + "=" + v + "&");
          }
      }
      sb.append("key=" + paternerKey);
      String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
      return sign;
  } 
 
  
} 