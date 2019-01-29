package com.ipl.spider.framework.task.support;

import com.ipl.spider.framework.resource.Resource;

import java.net.URLConnection;

/**
 * @auther amxing
 * @desc
 * @createTime 2018/10/22 23:18
 */
public interface HttpSupport<T extends URLConnection, R extends Resource<?>> {

    R recv() throws Exception;

    default boolean isMultiTask() {
        return false;
    }

    default boolean hasNextPage() {
        return false;
    }

    default String getNextPageUrl() {
        return null;
    }

}
