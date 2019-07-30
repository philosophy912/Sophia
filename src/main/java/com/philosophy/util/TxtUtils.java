package com.philosophy.util;

import com.philosophy.exception.LowLevelException;
import com.philosophy.io.OpenReader;
import com.philosophy.io.OpenWriter;
import com.philosophy.tools.Closee;
import com.philosophy.txt.TxtReader;
import com.philosophy.txt.TxtWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;
import java.util.List;

/**
 * @author lizhe
 * @since V1.0.0 2019/7/30 22:28
 **/
public final class TxtUtils {

    private static Logger log = LogManager.getLogger(TxtUtils.class);

    private String charset;
    private boolean isAppend;

    public TxtUtils(String charset) {
        this.charset = charset;
    }

    public TxtUtils(String charset, boolean isAppend) {
        this.charset = charset;
        this.isAppend = isAppend;
    }

    private TxtReader getReader(Path path) throws IOException {
        OpenReader openReader = new OpenReader();
        openReader.setCharset(charset);
        Reader reader = openReader.open(path);
        return new TxtReader(reader);
    }

    private TxtWriter getWriter(Path path) throws IOException {
        OpenWriter openWriter = new OpenWriter();
        openWriter.setAppend(isAppend);
        Writer writer = openWriter.open(path);
        return new TxtWriter(writer);
    }


    public List<String> read(Path path) throws IOException {
        return getReader(path).read();
    }


    public void write(Path path, List<String[]> content) throws IOException {
        getWriter(path).write(content);
    }

    public void write(Path path, String[] content) throws IOException {
        getWriter(path).write(content);
    }

    public void write(Path path, String content) throws IOException {
        getWriter(path).write(content);
    }

}
