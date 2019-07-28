package com.philosophy.csv;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.supercsv.io.CsvListReader;
import com.philosophy.api.csv.ECSVType;
import com.philosophy.api.csv.ICsvReader;
import com.philosophy.tools.Closee;

import lombok.Setter;

public class CsvReader implements ICsvReader<String[]> {
    private static Logger log = LogManager.getLogger(CsvReader.class);
    @Setter
    private ECSVType csvType;
    private CsvListReader csvListReader;


    public CsvReader(final Reader reader) {
        csvListReader = new CsvListReader(reader, csvType.getCsvPreference());
    }


    @Override
    public List<String[]> read() throws IOException {
        checkReader();
        List<String[]> result = read();
        result.remove(0);
        return result;
    }

    @Override
    public String read(List<String[]> list, int column, int row) {
        if (isAvailable(list, column, row)) {
            return list.get(row - 1)[column - 1];
        } else {
            log.error("Value(" + column + "," + row + ") is not exist");
            return null;
        }
    }

    @Override
    public String read(List<String[]> list, String title, int row) {
        int column = getColumnIndex(list.get(0), title);
        if (column != -1) {
            return read(list, column, row);
        } else {
            log.error("column(" + column + ") is not exist");
            return null;
        }
    }

    @Override
    public List<String[]> readContent() throws IOException {
        checkReader();
        List<String[]> result = read();
        result.remove(0);
        return result;
    }

    @Override
    public String[] readTitle() throws IOException {
        checkReader();
        return csvListReader.getHeader(true);
    }

    @Override
    public void close() {
        Closee.close(csvListReader);
    }

    private void checkReader() throws IOException {
        if (csvListReader == null) {
            throw new IOException("csvListReader is null, open File first");
        }
    }

    /**
     * @param contentList
     * @param column
     * @param row
     * @return
     * @Title: isAvaliable
     * @Description: 判断自然行列是否存在
     */
    private boolean isAvailable(List<String[]> contentList, int column, int row) {
        // 列大于0，然后列小于title的列总值
        boolean columnJudge = (column > 0) && (column <= contentList.get(0).length);
        boolean rowJudge = (row > 0) && (row <= contentList.size());
        return columnJudge && rowJudge;
    }

    /**
     * @param strings
     * @param title
     * @return
     * @Title: getColumnIndex
     * @Description: 返回自然列
     */
    private int getColumnIndex(String[] strings, String title) {
        int column = 1;
        for (String s : strings) {
            if (title.equals(s)) {
                return column;
            }
            column++;
        }
        return -1;
    }
}
