package com.ipl.spider.framework.connector;


import com.ipl.spider.framework.resource.Resource;

/**
 * @auther amxing
 * @desc
 * @createTime 2018/11/3 23:43
 */
public interface Connector<T> {

    int MAX_REPEAT_TIME = 3;

    boolean connect(Object... args) throws Exception;

    <R extends Resource<?>> R getResource(Class<R> resourceType) throws Exception;

    default void close() {

    }

}
