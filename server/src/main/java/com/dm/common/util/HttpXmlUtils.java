package com.dm.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class HttpXmlUtils {

	private static Logger logger = LoggerFactory.getLogger(HttpXmlUtils.class);
	
	/**
     * h5支付时 解析返回的值并返回prepareid
     * @throws IOException 
     * @throws JDOMException 
     */
    public static Map<String, String> xml2Map(String xml) {
        //解析返回的xml
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        HashMap<String, String> map = new HashMap<String, String>();
        //创建一个DocumentBuilder的对象
        try {
            //创建DocumentBuilder对象
            DocumentBuilder db = dbf.newDocumentBuilder();
            //通过DocumentBuilder对象的parser方法加载books.xml文件到当前项目下
            Document document = db.parse(new InputSource(new StringReader(xml)));
            
            //String  return_code = document.getElementsByTagName("return_code");
            NodeList nodes = document.getChildNodes().item(0).getChildNodes();
            for(int i = 0; i < nodes.getLength(); ++i) {
            	Node node = nodes.item(i);
            	if (node != null) {
            		String name = node.getNodeName();
            		//logger.info("name:" + name + "," + node);
            		//logger.info("name:" + name);
            		if (node.getFirstChild() != null) {
            			//logger.info("value:..." );
            			String value = node.getFirstChild().getNodeValue();
            			//logger.info("value:" + value);
            			map.put(name, value);
            		}
            	} else {
            		logger.info("empty node");
            	}
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }        

        return map;
    }
    /**
     * 构造xml参数
     * @param xml
     * @return
     */
    public static String xmlH5Info(Unifiedorder unifiedorder){
        if(unifiedorder!=null){
            StringBuffer bf = new StringBuffer();
            bf.append("<xml>");

            bf.append("<appid><![CDATA[");
            bf.append(unifiedorder.getAppid());
            bf.append("]]></appid>");

            bf.append("<mch_id><![CDATA[");
            bf.append(unifiedorder.getMch_id());
            bf.append("]]></mch_id>");

            bf.append("<nonce_str><![CDATA[");
            bf.append(unifiedorder.getNonce_str());
            bf.append("]]></nonce_str>");

            bf.append("<sign><![CDATA[");
            bf.append(unifiedorder.getSign());
            bf.append("]]></sign>");

            bf.append("<body><![CDATA[");
            bf.append(unifiedorder.getBody());
            bf.append("]]></body>");


            bf.append("<attach><![CDATA[");
            bf.append(unifiedorder.getAttach());
            bf.append("]]></attach>");

            bf.append("<out_trade_no><![CDATA[");
            bf.append(unifiedorder.getOut_trade_no());
            bf.append("]]></out_trade_no>");

            bf.append("<total_fee><![CDATA[");
            bf.append(unifiedorder.getTotal_fee());
            bf.append("]]></total_fee>");

            bf.append("<spbill_create_ip><![CDATA[");
            bf.append(unifiedorder.getSpbill_create_ip());
            bf.append("]]></spbill_create_ip>");

            bf.append("<notify_url><![CDATA[");
            bf.append(unifiedorder.getNotify_url());
            bf.append("]]></notify_url>");

            bf.append("<trade_type><![CDATA[");
            bf.append(unifiedorder.getTrade_type());
            bf.append("]]></trade_type>");

            bf.append("<scene_info><![CDATA[");
            bf.append(unifiedorder.getScene_info());
            bf.append("]]></scene_info>");

            bf.append("</xml>");
            return bf.toString();
        }
        return "";
    }
/**
     * post请求并得到返回结果
     * @param requestUrl
     * @param requestMethod
     * @param output
     * @return
     */
    public static String httpsRequest(String requestUrl, String requestMethod, String output) {
        try{
            URL url = new URL(requestUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod(requestMethod);
            if (null != output) {
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(output.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            connection.disconnect();
            return buffer.toString();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return "";
    }
}