package com.ipl.spider.task;

import java.util.concurrent.ThreadFactory;

/**
 * @author dev
 */
public class TaskFactory implements ThreadFactory {

    private String name;

    public TaskFactory(String name) {
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, String.format("task-tread-[%s]", name));
    }

}
