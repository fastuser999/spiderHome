package com.client.spider.proxy;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.client.spider.util.HttpUtil;
import com.client.spider.util.PropertiesReader;

/**
 * Created by user on 2019/1/11.
 */
public class Mogu {
	private static Logger logger = LoggerFactory.getLogger(Mogu.class);
	private Object vpnLock = new Object();
	private List<ProxyInfo> vpn = Collections.synchronizedList(new LinkedList<ProxyInfo>());
	private static Mogu instance = new Mogu();

	public Proxy getTunnelProxy() {
		//String username = PropertiesReader.instance().getProperty("mogu.account");
		//String passwd = PropertiesReader.instance().getProperty("mogu.passwd");
		InetSocketAddress addr = new InetSocketAddress("transfer.mogumiao.com", 9001);
		// 创建HTTP类型代理对象
		Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
		// Proxy proxy = new Proxy("transfer.mogumiao.com",9001,username,
		// passwd);
		return proxy;
	}

	public static Mogu instance() {
		return instance;
	}
	
	private Mogu() {
		startVpnGetter();
	}
	
	public class ProxyInfo {
		public String ip;
		public int port;
		public long startTime;
		public long lastUsedTime;
		public AtomicInteger valid = new AtomicInteger(1);
		ProxyInfo(String ip, int port) {
			this.ip = ip;
			this.port = port;
			startTime = (new Date()).getTime();
		}

		public Proxy getProxy() {
			InetSocketAddress addr = new InetSocketAddress(ip, port);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
			return proxy;
		}
		
//		public void setInvalid() {
//			valid.set(0);
//		}
	}

	public void invalidProxy(ProxyInfo info) {
		synchronized (vpnLock) {
			vpn.remove(info);
			vpn.add(info);
		}
	}

	public ProxyInfo getProxyInfo() {
		while (true) {
			synchronized(vpnLock) {
				if (vpn.size() > 0) {
					ProxyInfo proxy = vpn.get(0);
					return proxy;
				}
			}

			logger.info("no vpn yet");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private ProxyInfo getProxy2() {
		while (true) {
			synchronized(vpnLock) {
				if (vpn.size() > 0) {
					ProxyInfo proxy = vpn.remove(0);
//					long now = (new Date()).getTime();
//					long ipFrequence = 1000;
//					if (proxy.valid.get() == 0) {
//						continue;
//					} else if (now - proxy.lastUsedTime <= ipFrequence) {
//						vpn.add(0, proxy);
//						continue;
//					}
//					
//					proxy.lastUsedTime = now;
					vpn.add(proxy);
					return proxy;
				}
			}
			
			logger.info("no vpn yet");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void startVpnGetter() {
		//vpn.add(new ProxyInfo("transfer.mogumiao.com", 9001));
		
		//vpn.add(new ProxyInfo("115.221.8.82", 24790));
		//vpn.add(new ProxyInfo("113.110.44.153", 26019));
				
		new Thread(new Runnable() {
			private void removeExpired() {
				synchronized(vpnLock) {
					int i = 0;
					while(i < vpn.size()) {
						ProxyInfo proxy = vpn.get(i);
						final long maxTime = 300* 1000;
						final long now = (new Date()).getTime();
						if (now - proxy.startTime >= maxTime) {
							vpn.remove(i);
							continue;
						} else {
							++i;
						}
					}
				}
			}
			
			private boolean fetchVPN() {
				try {
					final String url = PropertiesReader.instance().getProperty("mogu.apiUrl");
		        	JSONObject jsonResult = HttpUtil.httpGetJson(url);
		        	String code = jsonResult.getString("code");
		        	if (!"0".equals(code)) {
		        		logger.error("fetchVPN failed:" + code);
		        		return false;
		        	}
		        	JSONArray arr = jsonResult.getJSONArray("msg");
		        	for (int i = 0; i < arr.size(); ++i) {
		        		JSONObject item = arr.getJSONObject(i);
		        		synchronized(vpnLock) {
		        			logger.info("ip:{}, port:{}", item.getString("ip"), item.getIntValue("port"));
		        			vpn.add(new ProxyInfo(item.getString("ip"), item.getIntValue("port")));
		        		}
		        	}
		        	
		        	logger.info("fetch VPN succeed");
		        	return true;
		        } catch (Exception e) {
		            logger.error("fetchVPN failed:" + e.getMessage());
		        }

				return false;
			}
			
			@Override
			public void run() {
				long lastFetch = 0;
				while(true) {
					try {
						this.removeExpired();
						final long now = (new Date()).getTime();
						//final int keepCount = 2000;
						final int fetchDuration = 30000;
						if (now - lastFetch >= fetchDuration) {
							boolean fetched = fetchVPN();
							lastFetch = now;
//							if (fetched) {
//								lastFetch = now;
//							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						logger.error(ex.getMessage());
					}
				}
			}
		}).start();
	}
}
