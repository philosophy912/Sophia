package com.philosophy.csv.common;

import com.philosophy.base.common.Closee;
import com.philosophy.csv.api.CsvEnum;
import com.philosophy.csv.api.ICsvReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhe
 * @date 2019/10/16:12:46
 */
@Slf4j
public class CsvReader implements ICsvReader<String[]> {

    private Reader reader;
    private CsvEnum csvEnum;
    private Iterable<CSVRecord> records;

    public CsvReader(Reader reader, CsvEnum csvEnum) {
        this.reader = reader;
        this.csvEnum = csvEnum;
    }

    /**
     * 判断自然行列是否存在
     *
     * @param contentList 内容
     * @param column      列
     * @param row         行
     * @return 判断的结果
     */
    private boolean isAvailable(List<String[]> contentList, int column, int row) {
        // 列大于0，然后列小于title的列总值
        boolean columnJudge = (column > 0) && (column <= contentList.get(0).length);
        boolean rowJudge = (row > 0) && (row <= contentList.size());
        return columnJudge && rowJudge;
    }

    /**
     * 返回自然列
     *
     * @param strings 列所在的内容
     * @param title   列名
     * @return 列所在的列号
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

    /**
     * 读取所有内容，设置 CSVRecord
     *
     * @throws IOException IO异常
     */
    private void readAll() throws IOException {
        this.records = csvEnum.getCsvFormat().parse(reader);
    }


    @Override
    public String[] readTitle() throws IOException {
        readAll();
        String[] content = null;
        for (CSVRecord record : records) {
            int size = record.size();
            content = new String[size];
            for (int i = 0; i < size; i++) {
                content[i] = record.get(i);
            }
            break;
        }
        return content;
    }

    @Override
    public List<String[]> readContent() throws IOException {
        List<String[]> contents = read();
        contents.remove(0);
        return contents;
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
    public String read(List<String[]> list, int column, int row) {
        if (isAvailable(list, column, row)) {
            return list.get(row - 1)[column - 1];
        } else {
            log.error("Value(" + column + "," + row + ") is not exist");
            return null;
        }
    }

    @Override
    public List<String[]> read() throws IOException {
        readAll();
        List<String[]> contents = new ArrayList<>();
        for (CSVRecord record : records) {
            int size = record.size();
            String[] content = new String[size];
            for (int i = 0; i < size; i++) {
                content[i] = record.get(i);
            }
            contents.add(content);
        }
        return contents;
    }

    @Override
    public void close() {
        Closee.close(reader);
    }
}
