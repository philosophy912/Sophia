package com.philosophy.api.txt;

import java.io.IOException;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 23:25
 **/
public interface ITxtWriter<T> extends IWriter<T> {
    /**
     * 写入内容
     * @param t 要写入的内容
     * @throws IOException 抛出异常
     */
    void write(T t) throws IOException;
}
