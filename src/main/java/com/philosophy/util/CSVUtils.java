package com.philosophy.util;

import com.philosophy.api.csv.ECSVType;
import com.philosophy.csv.CsvReader;
import com.philosophy.csv.CsvWriter;
import com.philosophy.tools.Parse;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/24 23:17
 **/
@Setter
public class CSVUtils {
    private static Logger logger = LogManager.getLogger(CSVUtils.class);
    private CsvReader reader;
    private CsvWriter writer;

    private ECSVType type = ECSVType.COMMA;
    private String charset = "utf-8";
    private boolean isAppend = true;

    public CSVUtils() {
        reader = new CsvReader(type, charset);
        writer = new CsvWriter(type, isAppend, charset);
    }

    public CSVUtils(ECSVType type, String charset, boolean isAppend) {
        this.type = type;
        this.charset = charset;
        this.isAppend = isAppend;
    }


    public String[] readTitle(Path path) {
        String[] result = null;
        try {
            reader.open(path);
            result = reader.readTitle();
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            reader.close();
            return result;
        }
    }

    public List<String[]> readContent(Path path) {
        List<String[]> result = null;
        try {
            reader.open(path);
            result = reader.readContent();
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            reader.close();
            return result;
        }
    }

    public List<String[]> readAll(Path path) {
        List<String[]> result = null;
        try {
            reader.open(path);
            result = reader.read();
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            reader.close();
            return result;
        }
    }

    public String readContent(Path path, String title, int row) {
        String result = null;
        try {
            reader.open(path);
            List<String[]> content = reader.read();
            result = reader.read(content, title, row);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            reader.close();
            return result;
        }
    }

    public String readContent(Path path, int column, int row) {
        String result = null;
        try {
            reader.open(path);
            List<String[]> content = reader.read();
            result = reader.read(content, column, row);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            reader.close();
            return result;
        }
    }


    public void write(Path path, String[] content) {
        try {
            writer.open(path);
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }

    public void write(Path path, List<String[]> contents) {
        List<String> temp = (List<String>) Parse.toList(contents);
        String[] content = Parse.toArray(temp);
        write(path, content);
    }

}
