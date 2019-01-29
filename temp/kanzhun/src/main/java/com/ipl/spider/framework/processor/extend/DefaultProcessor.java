package com.ipl.spider.framework.processor.extend;

import com.ipl.spider.framework.annotation.TaskExtend;
import com.ipl.spider.framework.processor.Processor;
import com.ipl.spider.framework.processor.listener.extend.NullListener;
import com.ipl.spider.framework.task.Task;
import com.ipl.spider.framework.task.TaskQueue;
import com.ipl.spider.framework.task.TaskStrategy;
import com.ipl.spider.framework.task.cache.TaskCache;
import com.ipl.spider.framework.task.support.BatchTask;
import com.ipl.spider.framework.utils.ReflectUtils;
import com.ipl.spider.framework.utils.SpringContextHolder;
import com.ipl.spider.task.TaskFactory;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 默认爬虫任务处理器
 * @author dev
 */
@Component
public class DefaultProcessor extends Processor {

    public DefaultProcessor() {
        super();
    }

    @Override
    public <T extends Task> T generateTask(Class<T> c, TaskCache taskCache) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(c);
        enhancer.setCallbackFilter(method -> method.getName().equals("run") ? 1 : 0);
        enhancer.setCallbacks(new Callback[]{new NullListener(), this});
        return (T) enhancer.create(new Class[]{TaskCache.class}, new Object[]{taskCache});
    }

    public ThreadPoolExecutor getExecutor() {
        return new ThreadPoolExecutor(5, 10 ,
                2 , TimeUnit.MINUTES, new LinkedBlockingDeque<>(),
                new TaskFactory(Processor.class.getName()));
    }

    @Override
    public void doPrepare(Task o) {
        System.out.println("doPrepare");
    }

    @Override
    public void doRecovery(Task o) {
        System.out.println("doRecovery");
    }
}
