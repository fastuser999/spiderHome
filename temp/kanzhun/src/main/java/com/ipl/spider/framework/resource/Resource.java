package com.ipl.spider.framework.resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * @auther amxing
 * @desc
 * @createTime 2018/11/4 2:08
 */
public interface Resource<T> {

    String getName();

    int size();

    void readData(T data);

    void readData(InputStream is) throws IOException;

    byte[] getByte();

    InputStream getInputStream();

}
