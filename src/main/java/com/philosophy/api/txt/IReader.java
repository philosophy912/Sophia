package com.philosophy.api.txt;

import java.io.IOException;
import java.util.List;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 22:36
 **/
public interface IReader<T> extends AutoCloseable {
    /**
     * 读取内容
     *
     * @return 返回读取到的内容的列表
     * @throws IOException 抛出异常
     */
    List<T> read() throws IOException;
}
