package com.ipl.spider.framework.annotation;

import com.ipl.spider.framework.processor.Processor;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TaskExtend {

    Class<? extends Processor> processor();

}
