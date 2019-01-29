package com.ipl.spider.framework.config;

import com.ipl.spider.framework.processor.Processor;
import com.ipl.spider.task.TaskFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @auther amxing
 * @desc
 * @createTime 2018/12/6 19:26
 */
@Configuration
public class AppConfig {

    @Bean("defaultThreadPool")
    public ThreadPoolExecutor defaultThreadPool() {
        return new ThreadPoolExecutor(5, 10 ,
                2 , TimeUnit.MINUTES, new LinkedBlockingDeque<>(),
                new TaskFactory(Processor.class.getName()));
    }

}
