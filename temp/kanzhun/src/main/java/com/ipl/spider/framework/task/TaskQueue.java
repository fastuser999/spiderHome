package com.ipl.spider.framework.task;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * @auther amxing
 * @desc
 * @createTime 2018/8/18 23:53
 */
public class TaskQueue<T extends Class<? extends Task>> extends ConcurrentLinkedQueue<T> implements Cloneable {

    public TaskQueue() {
    }

    public TaskQueue(Collection c) {
        super(c);
    }

    @Override
    public TaskQueue<T> clone() {
        return new TaskQueue<>(this.stream().collect(Collectors.toList()));
    }

}
