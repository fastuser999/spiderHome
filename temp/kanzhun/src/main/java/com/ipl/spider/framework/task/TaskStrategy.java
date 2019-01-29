package com.ipl.spider.framework.task;

import com.ipl.spider.framework.processor.Processor;
import com.ipl.spider.framework.processor.extend.DefaultProcessor;
import com.ipl.spider.framework.utils.SpringContextHolder;
import com.ipl.spider.web.task.kzw.DeepSearchTask;

import java.util.Arrays;

/**
 * @auther amxing
 * @desc
 * @createTime 2018/8/12 14:31
 */
public enum TaskStrategy {
    KANZHUN("看准网", DefaultProcessor.class, DeepSearchTask.class);

    private String desc;

    private Class<? extends Processor> processor;

    private Class<? extends Task>[] taskQueue;

    TaskStrategy(String desc, Class<? extends Processor> processor, Class<? extends Task>... c) {
        this.desc = desc;
        this.processor = processor;
        this.taskQueue = c;
    }

    public String getDesc() {
        return desc;
    }

    public Processor getProcessor() {
        return SpringContextHolder.getBean(processor);
    }

    public TaskQueue<Class<? extends Task>> getTaskQueue() {
        return new TaskQueue<>(Arrays.asList(taskQueue));
    }
}
