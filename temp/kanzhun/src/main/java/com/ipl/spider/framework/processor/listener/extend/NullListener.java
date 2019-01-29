package com.ipl.spider.framework.processor.listener.extend;

import com.ipl.spider.framework.processor.listener.Listener;
import com.ipl.spider.framework.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @auther amxing
 * @desc
 * @createTime 2018/8/12 16:33
 */
public class NullListener<T extends Task> implements Listener {

    private Logger logger = LoggerFactory.getLogger(NullListener.class);

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        this.doPrepare((T) o);
        Object result = methodProxy.invokeSuper(o, objects);
        this.doRecovery((T) o);
        return  result;
    }
}
