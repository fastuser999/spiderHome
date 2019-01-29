package com.ipl.spider.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtils {

    private Logger logger;

    public LoggerUtils() {
        this.logger = LoggerFactory.getLogger(this.getClass().getName());
    }

    public void info(String format, Object... args) {
        this.logger.info(format, args);
    }

    public void warn(String format, Object... args) {
        this.logger.warn(format, args);
    }

    public void error(String format, Object... args) {
        this.logger.error(format, args);
    }

}
