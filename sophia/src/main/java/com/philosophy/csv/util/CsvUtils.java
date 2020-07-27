package com.philosophy.csv.util;

import com.philosophy.base.util.InOutUtils;
import com.philosophy.csv.api.CsvEnum;
import com.philosophy.csv.common.CsvReader;
import com.philosophy.csv.common.CsvWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;
import java.util.List;

/**
 * @author lizhe
 * @date 2019/10/12:17:12
 */
@Slf4j
public class CsvUtils {

    /**
     * 获取实例化后的CsvReader
     *
     * @param path    csv文件
     * @param charset 编码格式
     * @param type    读写方式
     * @return 实例化后的CsvReader
     * @throws IOException IO异常
     */
    private CsvReader getReader(Path path, String charset, CsvEnum type) throws IOException {
        log.debug("read file [{}]", path);
        Reader reader = InOutUtils.openReader(path, charset);
        return new CsvReader(reader, type);
    }

    /**
     * 获取实例化后的CsvWriter
     *
     * @param path     csv文件
     * @param charset  编码格式
     * @param type     读写方式
     * @param isAppend 是否换行
     * @return 实例化后的CsvWriter
     * @throws IOException IO异常
     */
    private CsvWriter getWriter(Path path, String charset, CsvEnum type, boolean isAppend) throws IOException {
        log.debug("write file [{}]", path);
        Writer writer = InOutUtils.openWriter(path, charset, isAppend);
        return new CsvWriter(writer, type);
    }

    /**
     * 读取CSV的title行
     *
     * @param path    指定的CSV文件
     * @param charset 编码格式
     * @param type    读写方式
     * @return 读取到的内容
     * @throws IOException IO异常
     */
    public String[] readTitle(Path path, String charset, CsvEnum type) throws Exception {
        CsvReader reader = getReader(path, charset, type);
        String[] contents = reader.readTitle();
        reader.close();
        return contents;
    }

    /**
     * 读取CSV的内容
     *
     * @param path    指定的CSV文件
     * @param charset 编码格式
     * @param type    读写方式
     * @return 读取的内容
     * @throws IOException IO异常
     */
    public List<String[]> readContent(Path path, String charset, CsvEnum type) throws Exception {
        CsvReader reader = getReader(path, charset, type);
        List<String[]> contents = reader.readContent();
        reader.close();
        return contents;
    }

    /**
     * 读取指定CSV文件的指定列和行
     *
     * @param path    指定的CSV文件
     * @param charset 编码格式
     * @param type    读写方式
     * @param column  列
     * @param row     行
     * @return 读取的内容
     * @throws IOException IO异常
     */
    public String read(Path path, String charset, CsvEnum type, int column, int row) throws IOException {
        CsvReader reader = getReader(path, charset, type);
        List<String[]> content = reader.read();
        return reader.read(content, column, row);

    }

    /**
     * 读取指定CSV文件的指定title的列和行
     *
     * @param path    指定的CSV文件
     * @param charset 编码格式
     * @param type    读写方式
     * @param title   指定的title列
     * @param row     行
     * @return 读取的内容
     * @throws IOException IO异常
     */
    public String read(Path path, String charset, CsvEnum type, String title, int row) throws Exception {
        CsvReader reader = getReader(path, charset, type);
        List<String[]> content = reader.read();
        reader.close();
        return reader.read(content, title, row);
    }

    /**
     * 读取指定CSV文件的所有内容
     *
     * @param path    指定的CSV文件
     * @param charset 编码格式
     * @param type    读写方式
     * @return 读取的内容
     * @throws IOException IO异常
     */
    public List<String[]> read(Path path, String charset, CsvEnum type) throws Exception {
        CsvReader reader = getReader(path, charset, type);
        List<String[]> content = reader.read();
        reader.close();
        return content;
    }

    /**
     * 写入内容到指定的文件中
     *
     * @param path     指定的CSV文件
     * @param charset  编码格式
     * @param type     读写方式
     * @param isAppend 是否追加写入
     * @param content  内容
     * @throws IOException IO异常
     */
    public void write(Path path, String charset, CsvEnum type, boolean isAppend,
                      String[] content) throws Exception {
        CsvWriter writer = getWriter(path, charset, type, isAppend);
        writer.write(content);
        writer.close();
    }

    /**
     * 写入内容到指定的文件中
     *
     * @param path     指定的CSV文件
     * @param charset  编码格式
     * @param type     读写方式
     * @param isAppend 是否追加写入
     * @param content  内容
     * @throws IOException IO异常
     */
    public void write(Path path, String charset, CsvEnum type, boolean isAppend,
                      List<String[]> content) throws Exception {
        CsvWriter writer = getWriter(path, charset, type, isAppend);
        writer.write(content);
        writer.close();
    }
}
