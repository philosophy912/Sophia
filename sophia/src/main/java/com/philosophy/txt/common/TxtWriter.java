package com.philosophy.txt.common;

import com.philosophy.base.common.Closee;
import com.philosophy.txt.api.ITxtWriter;
import lombok.Setter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * @author lizhe
 */
public class TxtWriter implements ITxtWriter<String> {

    @Setter
    private BufferedWriter bw;
    @Setter
    private boolean isWarp;

    private static int WRITE_LINE_LIMIT = 100;

    public TxtWriter(final Writer writer) {
        bw = new BufferedWriter(writer);
    }

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
        if ((line % WRITE_LINE_LIMIT) == 0) {
            flush();
        }
    }

    /**
     * 外部数据用来flush
     *
     * @param i 行数
     * @throws IOException IO异常
     */
    public void flush(int i) throws IOException {
        if ((i % WRITE_LINE_LIMIT) == 0) {
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
    public void close() throws IOException {
        flush();
        Closee.close(bw);
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
