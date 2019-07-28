package com.philosophy.api.txt;

import java.io.IOException;
import java.util.List;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 22:36
 **/
public interface IWriter<T> extends AutoCloseable {
    /**
     * 写入内容
     *
     * @param t 要写入的内容
     * @throws IOException 抛出异常
     */
    void write(T[] t) throws IOException;

    /**
     * 写入内容
     *
     * @param t 要写入的内容a
     * @throws IOException 抛出异常
     */
    void write(List<T[]> t) throws IOException;
}
