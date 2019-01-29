package com.ipl.spider.framework.processor.listener;

import com.ipl.spider.framework.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.MethodInterceptor;

/**
 * @author dev
 * @desc
 * @createTime 2018/8/12 16:32
 */
public interface Listener<T extends Task> extends MethodInterceptor {

    Logger logger = LoggerFactory.getLogger(Listener.class);

    /**
     * 执行任务准备工作
     */
    default void doPrepare(T o){

    }

    /**
     * 数据回收工作
     */
    default void doRecovery(T o) {

    }

}
