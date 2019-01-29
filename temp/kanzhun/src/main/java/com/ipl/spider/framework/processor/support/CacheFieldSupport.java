package com.ipl.spider.framework.processor.support;

import com.ipl.spider.framework.task.cache.TaskCache;

/**
 * @auther amxing
 * @desc
 * @createTime 2018/10/30 23:45
 */
public interface CacheFieldSupport {

    default void cacheFileWrap(TaskCache taskCache, Object proxy, Class<?> c) {

    }

}
