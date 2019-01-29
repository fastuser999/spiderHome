package com.ipl.spider.framework.connector.http;

import com.ipl.spider.framework.connector.Connector;
import com.ipl.spider.framework.connector.exception.HttpConnectionException;
import com.ipl.spider.framework.resource.Resource;
import com.ipl.spider.framework.utils.PubTools;
import com.ipl.spider.framework.utils.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther amxing
 * @desc
 * @createTime 2018/11/3 23:50
 */
public class HttpConnector<T extends URLConnection> implements Connector {

    private Logger logger = LoggerFactory.getLogger(HttpConnector.class);

    private int repeat;

    private String site;

    private Charset charSet;

    private Proxy proxy;

    private Map<String, Object> headerMap;

    private Integer timeout;

    private T connect;

    public HttpConnector() {
    }

    public HttpConnector(Map<String, Object> args) {
        this.site = (String) args.get("site");
        this.proxy = (Proxy) args.get("proxy");
        this.charSet = (Charset) args.get("charSet");
        this.headerMap = (Map<String, Object>) args.get("headerMap");
        this.timeout = (Integer) args.get("timeout");
    }

    @Override
    public boolean connect(Object... args) throws Exception {
        try {
            if (this.connect != null) {
                return true;
            }
            this.repeat++;
            logger.info("尝试第[{}]次请求url：[{}]",repeat, site);
            if (PubTools.isEmpty(site)) {
                throw new HttpConnectionException(null, "目标站点[site]参数不能为空");
            }
            URL url = new URL(site);
            this.connect = (T) (PubTools.isEmpty(proxy) ?  url.openConnection() : url.openConnection(proxy));
            if (!PubTools.isEmpty(headerMap)) {
                headerMap.forEach((k, v) -> connect.addRequestProperty(k, String.valueOf(v)));
            }
            if (!PubTools.isEmpty(timeout)) {
                connect.setConnectTimeout(timeout);
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        return this.connect != null && this.connect.getInputStream() != null;
    }

    @Override
    public Resource<?> getResource(Class resourceType) throws Exception {
        //大于最大重试次数就报错
        if (this.repeat > Connector.MAX_REPEAT_TIME) {
            throw new HttpConnectionException(site, "重复次数超过" + Connector.MAX_REPEAT_TIME + "次");
        }
        if (!this.connect()) {
            return getResource(resourceType);
        }
        return (Resource<?>) ReflectUtils.getInstance(resourceType, new Class[]{InputStream.class}, new Object[]{this.connect.getInputStream()});

    }

    public static class Builder {

        private Map<String, Object> args;

        private static Builder builder;

        private Builder() {
            this.args = new HashMap<>();
        }

        public static Builder create() {
            if (builder == null) {
                builder = new Builder();
            } else {
                builder.args = new HashMap<>();
            }
            return builder;
        }

        public Builder site(String site) {
            args.put("site", site);
            return builder;
        }

        public Builder proxy(Proxy proxy) {
            args.put("proxy", proxy);
            return builder;
        }

        public Builder header(Map<String, Object> headerMap) {
            args.put("headerMap", headerMap);
            return builder;
        }

        public Builder  timeout(int timeout) {
            args.put("timeout", timeout);
            return builder;
        }

        public HttpConnector build() {
            return new HttpConnector(args);
        }

    }

}
