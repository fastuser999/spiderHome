package com.ipl.spider.framework.task;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @auther amxing
 * @desc
 * @createTime 2018/10/28 0:41
 */
public class TaskProxy<T extends Task> implements InvocationHandler {

    private T target;

    public TaskProxy(T target) {
        this.target = target;
    }

    /*public ProxyAbility getTaskProxy() {
        return (ProxyAbility) Proxy.newProxyInstance(ProxyAbility.class.getClassLoader(), ProxyAbility.class.getInterfaces(), this);
    }*/

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("amxing");
        method.invoke(this.target, args);
        Field field = proxy.getClass().getSuperclass().getDeclaredField("h");
        field.setAccessible(true);
        Object obj = field.get(proxy);
        Field f = TaskProxy.class.getDeclaredField("target");
        f.setAccessible(true);
        T task = (T)f.get(obj);
        return null;
    }

    private T getTarget(Object proxy) throws Exception {
        try {
            Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
            h.setAccessible(true);
            Object obj = h.get(proxy);
            Field f = obj.getClass().getDeclaredField("target");
            return (T) f.get(obj);
        } catch (Exception e) {
            return null;
        }
    }

}
