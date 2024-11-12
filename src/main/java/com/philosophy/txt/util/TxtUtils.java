package com.philosophy.txt.util;

import com.philosophy.base.util.InOutUtils;
import com.philosophy.txt.common.TxtReader;
import com.philosophy.txt.common.TxtWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.List;

/**
 * @author lizhe
 * @since V1.0.0 2019/7/30 22:28
 **/
@Slf4j
public class TxtUtils {


    private TxtReader getReader(Path path, String charset, boolean skipBlankLine) throws IOException {
        Reader reader = InOutUtils.openReader(path, charset);
        return new TxtReader(reader, skipBlankLine);
    }

    private TxtWriter getWriter(Path path, String charset, boolean isAppend, boolean isWrap) throws IOException {
        Writer writer = InOutUtils.openWriter(path, charset, isAppend);
        TxtWriter txtWriter = new TxtWriter(writer);
        txtWriter.setWarp(isWrap);
        return txtWriter;
    }

    /**
     * 从txt文件中读取内容
     *
     * @param path    文件
     * @param charset 编码格式
     * @return 内容
     * @throws IOException IO异常
     */
    public List<String> read(Path path, String charset, boolean skipBlankLine) throws IOException {
        TxtReader reader = getReader(path, charset, skipBlankLine);
        List<String> contents = reader.read();
        reader.close();
        return contents;
    }

    /**
     * 从txt文件中读取内容
     *
     * @param inputStream 输入流
     * @param charset 编码格式
     * @return 内容
     * @throws IOException IO异常
     */
    public List<String> read(InputStream inputStream, String charset, boolean skipBlankLine) throws IOException {
        TxtReader reader = new TxtReader(new BufferedReader(new InputStreamReader(inputStream, charset)), skipBlankLine);
        List<String> contents = reader.read();
        reader.close();
        return contents;
    }

    /**
     * 将内容写入到文件中
     *
     * @param path     文件
     * @param content  内容
     * @param charset  编码格式
     * @param isAppend 是否追加
     * @param isWrap   是否换行
     * @throws IOException IO异常
     */
    public void write(Path path, List<String[]> content, String charset,
                      boolean isAppend, boolean isWrap) throws IOException {
        TxtWriter writer = getWriter(path, charset, isAppend, isWrap);
        writer.write(content);
        writer.close();
    }

    /**
     * 将内容写入到文件中
     *
     * @param path     文件
     * @param content  内容
     * @param charset  编码格式
     * @param isAppend 是否追加
     * @param isWrap   是否换行
     * @throws IOException IO异常
     */
    public void write(Path path, String[] content, String charset,
                      boolean isAppend, boolean isWrap) throws IOException {
        TxtWriter writer = getWriter(path, charset, isAppend, isWrap);
        writer.write(content);
        writer.close();
    }

    /**
     * 将内容写入到文件中
     *
     * @param path     文件
     * @param content  内容
     * @param charset  编码格式
     * @param isAppend 是否追加
     * @param isWrap   是否换行
     * @throws IOException IO异常
     */
    public void write(Path path, String content, String charset, boolean isAppend, boolean isWrap) throws IOException {
        TxtWriter writer = getWriter(path, charset, isAppend, isWrap);
        writer.write(content);
        writer.close();
    }

}
