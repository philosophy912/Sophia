package com.philosophy.util;

import com.philosophy.api.csv.ECSVType;
import com.philosophy.csv.CsvReader;
import com.philosophy.csv.CsvWriter;
import com.philosophy.io.OpenReader;
import com.philosophy.io.OpenWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;
import java.util.List;

/**
 * @author lizhe
 * @since V1.0.0 2019/7/30 22:43
 **/
public final class CSVUtils {
    private static Logger log = LogManager.getLogger(CSVUtils.class);

    private String charset;
    private boolean isAppend;
    private ECSVType type;

    public CSVUtils(ECSVType type, String charset) {
        this.charset = charset;
        this.type = type;
    }

    public CSVUtils(ECSVType type, String charset, boolean isAppend) {
        this.charset = charset;
        this.type = type;
        this.isAppend = isAppend;
    }

    private CsvReader getReader(Path path) throws IOException {
        OpenReader openReader = new OpenReader();
        openReader.setCharset(charset);
        Reader reader = openReader.open(path);
        CsvReader csvReader = new CsvReader(reader);
        csvReader.setCsvType(type);
        return csvReader;
    }

    private CsvWriter getWriter(Path path) throws IOException {
        OpenWriter openWriter = new OpenWriter();
        openWriter.setAppend(isAppend);
        Writer writer = openWriter.open(path);
        CsvWriter csvWriter = new CsvWriter(writer);
        csvWriter.setType(type);
        return csvWriter;
    }

    public String[] readTitle(Path path) throws IOException {
        return getReader(path).readTitle();
    }

    public List<String[]> readContent(Path path) throws IOException {
        return getReader(path).readContent();
    }

    public String read(Path path, int column, int row) throws IOException {
        CsvReader reader = getReader(path);
        List<String[]> content = reader.readContent();
        return reader.read(content, column, row);

    }

    public String read(Path path, String title, int row) throws IOException {
        CsvReader reader = getReader(path);
        List<String[]> content = reader.readContent();
        return reader.read(content, title, row);
    }

    public List<String[]> read(Path path) throws IOException {
        CsvReader reader = getReader(path);
        return reader.readContent();
    }

    public void write(Path path, String[] content) throws IOException {
        getWriter(path).write(content);
    }

    public void write(Path path, List<String[]> content) throws IOException {
        getWriter(path).write(content);
    }

}
