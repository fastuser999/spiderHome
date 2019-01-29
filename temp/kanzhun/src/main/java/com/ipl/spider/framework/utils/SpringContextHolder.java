package com.ipl.spider.framework.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @auther amxing
 * @desc
 * @createTime 2018/8/19 9:20
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    public static <T>  T getBean(Class<T> c) {
        return context.getBean(c);
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public static ApplicationContext getContext() {
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
