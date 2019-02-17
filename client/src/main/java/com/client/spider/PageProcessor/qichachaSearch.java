package com.client.spider.PageProcessor;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.client.spider.entry.KeywordsMode;
import com.client.spider.proxy.Mogu;
import com.client.spider.qichacha.keyword.Dict;
import com.client.spider.util.PropertiesReader;
import com.client.spider.util.RandomUserAgent;

import Storage.MySQLStorage;
import Storage.MySQLStorage.CompanyEntity;

public class qichachaSearch {
	private static Logger logger = LoggerFactory.getLogger(KeywordsMode.class);
    public static final String ROOT = "http://m.qichacha.com/search?key=";

    public qichachaSearch() {
    	
    }
    
    private Map<String, String> setHeader(Connection con) {
		final String DOMAIN ="m.qichacha.com";
		//final String appkey  = PropertiesReader.instance().getProperty("mogu.appkey");
		final String appkey  = PropertiesReader.instance().getProperty("mogu.apiAppkey");
		
		Map<String, String> headers = new HashMap<>();
        headers.put("content-type", "text/html");
        headers.put("Connection", "keep-alive");
        headers.put("Host", DOMAIN);
        headers.put("Proxy-Authorization","Basic "+ appkey);
        con.headers(headers);
        con.userAgent(RandomUserAgent.getRandomUserAgent());
        
        return headers;
	}
    
    public boolean run(long id) {
    	String keywords = Dict.getOne(id);
        System.out.println("id:" + id+ ", word:" + keywords);
        logger.info("id:{}, word:{}", id, keywords);
    	Connection con = null;
    	List<CompanyEntity> cList = new LinkedList<CompanyEntity>();
    	MySQLStorage storage = MySQLStorage.Instance();
    	storage.initId(id);
    	
        try {
    		con = HttpConnection.connect(ROOT + URLEncoder.encode(keywords, "UTF-8"));
    		this.setHeader(con);
    		//InetSocketAddress addr = new InetSocketAddress(proxyInfo.ip, proxyInfo.port);
    		//Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
    		//con.proxy(Mogu.instance().getProxy());
            
			Document doc = con.get();
			Elements links = doc.select("a[href]");
			for (Element link : links) {
				String href = link.attr("href");
				if (!(href != null && href.startsWith("/firm_") && href.endsWith(".shtml"))) {
					continue;
				}
				
				String companyName = link.select("div.list-item-name").first().text();
				String gs_status = link.select("div.list-item-status").first().text();
				
				logger.info("href:{}, companyName:{}", href, companyName);
				
				cList.add(storage.new CompanyEntity(companyName, href, id, 0, gs_status));
			}
			
			if (cList.size() > 0) {
				storage.addCompany(cList);
				storage.succeedId(id);
			} else {
				links = doc.select("div.nodata");
				if (links.size() > 0 && "小查未找到数据".equals(links.first().text())) {
					logger.info("no data:{}", keywords);
					storage.unknownId(id, "nodata");
				} else {
					logger.info("maybe ip block:{}", keywords);
					storage.failedId(id, "maybe ip block");
				}
			}
		} catch (Exception e) {
			logger.error("read qichacha err:" + e.getMessage());
			storage.failedId(id, e.getMessage());
			//proxyInfo.setInvalid();
			return false;
			
//			if (con != null && con.response().statusCode() != 200) {
//				int code = con.response().statusCode();
//				storage.failedId(id, code);
//				if ((code / 100) == 4) {
//					// re run
//					proxyInfo.setInvalid();
//					return false;
//				}
//			} else {
//				storage.failedId(id, e.getMessage());
//			}
		}
        
        return true;
    }

    public static void main(String[] args) {
        (new qichachaSearch()).run(38000);
    }
}
