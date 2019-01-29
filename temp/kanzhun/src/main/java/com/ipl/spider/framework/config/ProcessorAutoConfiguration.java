package com.ipl.spider.framework.config;

import com.ipl.spider.framework.processor.support.ConstTag;
import com.ipl.spider.framework.utils.SpringContextHolder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author dev
 */
@Component
@ConfigurationProperties(prefix = "spider.processor")
public class ProcessorAutoConfiguration {

    private String threadPool;

    private ConstTag status;

    public ProcessorAutoConfiguration() {
    }

    public ProcessorAutoConfiguration(String threadPool, ConstTag status) {
        this.threadPool = threadPool;
        this.status = status;
    }

    public ThreadPoolExecutor getThreadPool() {
        return (ThreadPoolExecutor) SpringContextHolder.getBean(threadPool);
    }

    public void setThreadPool(String threadPool) {
        this.threadPool = threadPool;
    }

    public ConstTag getStatus() {
        return status;
    }

    public void setStatus(ConstTag status) {
        this.status = status;
    }

    public static ProcessorAutoConfiguration orElse(ProcessorAutoConfiguration conf) {
        if (conf != null) {
            return conf;
        }
        return new ProcessorAutoConfiguration("defaultThreadPool", ConstTag.ACTIVE);
    }

}
