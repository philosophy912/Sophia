package com.philosophy.base.util;


import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @author lizhe
 * @date 2019/10/12:16:20
 */
public class InOutUtils extends IOUtils {
    /**
     * 打开InputStream流
     *
     * @param path 文件
     * @return InputStream流
     * @throws IOException IO异常
     */
    public static InputStream openInputStream(Path path) throws IOException {
        return Files.newInputStream(path);
    }

    /**
     * 打开文件为Reader
     *
     * @param path    文件
     * @param charset 编码格式
     * @return Reader
     * @throws IOException IO异常
     */
    public static Reader openReader(Path path, String charset) throws IOException {
        return new InputStreamReader(openInputStream(path), charset);
    }

    /**
     * 打开文件为Writer
     *
     * @param path     文件
     * @param charset  编码格式
     * @param isAppend 是否换行
     * @return Writer
     * @throws IOException IO异常
     */
    public static Writer openWriter(Path path, String charset, boolean isAppend) throws IOException {
        if (isAppend) {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            return new OutputStreamWriter(Files.newOutputStream(path,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.APPEND), charset);
        }
        return new OutputStreamWriter(Files.newOutputStream(path,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE), charset);
    }
}
