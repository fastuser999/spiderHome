package com.ipl.spider.framework.connector.exception;

/**
 * @author dev
 */
public class HttpConnectionException extends Exception {

    private String site;

    private String msg;

    public HttpConnectionException(String site, String msg) {
        this.site = site;
        this.msg = msg;
    }

    @Override
    public String getLocalizedMessage() {
        return "请求站点:[" + site + "]，失败:" + msg;
    }

}
