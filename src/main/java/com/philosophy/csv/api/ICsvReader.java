package com.philosophy.csv.api;

import com.philosophy.base.api.IReader;

import java.io.IOException;
import java.util.List;

/**
 * @param <T>
 * @author totti_912@sina.com
 */
public interface ICsvReader<T> extends IReader<T> {
    /**
     * 读取 CSV文档的title列
     *
     * @return 列的集合
     * @throws IOException IO异常
     */
    T readTitle() throws IOException;

    /**
     * 读取 CSV文档的所有内容（除了title列）
     *
     * @return 列的集合
     * @throws IOException IO异常
     */
    List<T> readContent() throws IOException;

    /**
     * 把LIST当成一个二维数组，读取指定title列的row行读取数据
     *
     * @param list  数据
     * @param title title列的名字
     * @param row   行数
     * @return 读取到的数据
     */
    String read(List<T> list, String title, int row);

    /**
     * 把LIST当成一个二维数组，读取指定column列的row行读取数据
     *
     * @param list   数据
     * @param column 列数
     * @param row    行数
     * @return 读取到的数据
     */
    String read(List<T> list, int column, int row);
}
