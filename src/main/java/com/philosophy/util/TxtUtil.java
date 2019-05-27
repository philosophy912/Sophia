package com.philosophy.util;

import com.philosophy.txt.TxtReader;
import com.philosophy.txt.TxtWriter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/24 23:21
 **/
public class TxtUtil {
    private static TxtReader txtReader = new TxtReader();
    private static TxtWriter txtWriter = new TxtWriter();
    private TxtUtil(){}
    /**
     *
     * @Title: read
     * @Description: 读取文件数据（大文件慎用)
     * @param path
     * @return
     * @throws IOException
     */
    public static List<String> read(Path path) throws IOException {
        txtReader.open(path);
        List<String> result = txtReader.read();
        txtReader.close();
        return result;
    }

    /**
     *
     * @Title: write
     * @Description: 写入文件
     * @param path
     * @param string
     * @throws IOException
     */
    public static void write(Path path, String string) throws IOException
    {
        txtWriter.open(path);
        txtWriter.write(string);
        txtWriter.close();
    }

    /**
     *
     * @Title: write
     * @Description: 写入文件
     * @param path
     * @param strings
     * @throws IOException
     */
    public static void write(Path path, String[] strings) throws IOException
    {
        txtWriter.open(path);
        txtWriter.write(strings);
        txtWriter.close();
    }

    /**
     *
     * @Title: write
     * @Description: 写入文件
     * @param path
     * @param list
     * @throws IOException
     */
    public static void write(Path path, List<String[]> list) throws IOException
    {
        txtWriter.open(path);
        txtWriter.write(list);
        txtWriter.close();
    }
}
