package com.ipl.spider.framework.resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * @auther amxing
 * @desc
 * @createTime 2018/11/4 2:18
 */
public abstract class TempResource<T> implements Resource<T> {


    private T data;

    public TempResource() {
    }

    public TempResource(T data) {
        this.data = data;
    }

    public TempResource(InputStream is) throws IOException {
        this.readData(is);
    }

    @Override
    public void readData(T data) {
        this.data = data;
    }

    @Override
    public int size() {
        return getByte().length;
    }

    @Override
    public String getName() {
        return null;
    }

    public T getData() {
        return this.data;
    }
}
