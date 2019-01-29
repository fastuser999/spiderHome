package com.ipl.spider.framework.processor.listener.extend;

import com.ipl.spider.framework.annotation.Getter;
import com.ipl.spider.framework.processor.listener.Listener;
import com.ipl.spider.framework.task.Task;
import com.ipl.spider.framework.task.TaskCode;
import com.ipl.spider.framework.task.cache.TaskCache;
import com.ipl.spider.framework.utils.ReflectUtils;
import org.slf4j.*;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author dev
 * @desc
 * @createTime 2018/8/15 23:22
 */
public interface TaskListener<T extends Task> extends Listener {

    Logger logger = LoggerFactory.getLogger(TaskListener.class);

    @Override
    default Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) {
        try {
            this.doPrepare((T) o);
            this.pullCacheField((T) o);
            methodProxy.invokeSuper(o, objects);
            this.pushCacheField((T) o);
            this.doRecovery((T) o);
        } catch (Throwable throwable) {
            return TaskCode.FAILURE;
        }
        return TaskCode.SUCCESS;
    }

    default void pullCacheField(T task) {
        try {
            TaskCache taskCache = task.getTaskCache();
            if (taskCache == null) {
                return;
            }
            task.setUrl(taskCache.getUrl());
            String taskName = task.getClass().getName();
            Class<T> taskClass = (Class<T>) Class.forName(taskName.substring(0, taskName.indexOf("$")));
            ReflectUtils.getAnnotatedFiled(taskClass, Getter.class)
                    .stream()
                    .forEach(field -> ReflectUtils.setFieldValue(task, field, taskCache.get(field.getName())));
        } catch (Exception e) {
            logger.error("从缓存中拉取字段值失败:{}", e.getLocalizedMessage());
        }
    }

    default void pushCacheField(T task) {

    }

}
