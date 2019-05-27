package com.philosophy.txt;

import com.philosophy.api.txt.ITxtWriter;
import com.philosophy.tools.Closee;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Setter
public class TxtWriter implements ITxtWriter<String> {
    private Logger logger = LogManager.getLogger(TxtWriter.class);

    private boolean isAppend = true;
    private boolean isWarp = true;
    private String charset = UTF8;
    private FileOutputStream fos;
    private OutputStreamWriter osw;
    private BufferedWriter bw;

    /**
     * 写入的txt的line值
     **/
    private int line = 0;

    private void flush() throws IOException {
        if (bw != null) {
            bw.flush();
        }
    }

    private void lineEnd() throws IOException {
        line++;
        if ((line % 100) == 0) {
            flush();
        }
    }

    /**
     * @param i
     * @throws IOException
     * @Title: flush
     * @Description: 外部数据用来flush
     */
    public void flush(int i) throws IOException {
        if ((i % 100) == 0) {
            flush();
        }
    }

    @Override
    public void write(String[] strings) throws IOException {
        for (String s : strings) {
            write(s);
            lineEnd();
        }
    }

    @Override
    public void write(List<String[]> stringList) throws IOException {
        for (String[] str : stringList) {
            write(str);
        }
    }


    @Override
    public void open(Path path) throws IOException {
        /**
         * READ	以读取方式打开文件
         * WRITE　　	已写入方式打开文件
         * CREATE	如果文件不存在，创建
         * CREATE_NEW	如果文件不存在，创建；若存在，异常。
         * APPEND	在文件的尾部追加
         * DELETE_ON_CLOSE	当流关闭的时候删除文件
         * TRUNCATE_EXISTING	把文件设置为0字节
         * SPARSE	文件不够时创建新的文件
         * SYNC	同步文件的内容和元数据信息随着底层存储设备
         * DSYNC	同步文件的内容随着底层存储设备
         */
        if (isAppend) {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            bw = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(path, StandardOpenOption.WRITE,
                    StandardOpenOption.APPEND), charset));
        }
        bw = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(path, StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE), charset));
    }

    @Override
    public void close() throws IOException {
        flush();
        Closee.close(bw, fos, osw);
    }

    @Override
    public void write(String string) throws IOException {
        if (bw == null) {
            throw new IOException("BufferedWriter is null");
        }
        bw.write(string);
        if (isWarp) {
            bw.write(System.lineSeparator());
        }
    }
}
