package com.philosophy.csv;

import com.philosophy.api.csv.ECSVType;
import com.philosophy.api.csv.ICsvReader;
import com.philosophy.tools.Closee;

import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.supercsv.io.CsvListReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.List;

@Setter
public class CsvReader implements ICsvReader<String[]> {
    private Logger logger = LogManager.getLogger(CsvReader.class);

    private ECSVType type = ECSVType.COMMA;
    private String charset = UTF8;

    private CsvListReader reader;
    private InputStreamReader isr;

    public CsvReader(){}

    public CsvReader(ECSVType type, String charset){
        this.type = type;
        this.charset = charset;
    }


    /**
     *
     * @Title: isAvaliable
     * @Description: 判断自然行列是否存在
     * @param contentList
     * @param column
     * @param row
     * @return
     */
    private boolean isAvaliable(List<String[]> contentList, int column, int row)
    {
        // 列大于0，然后列小于title的列总值
        boolean columnJudge = column > 0 && column <= contentList.get(0).length;
        boolean rowJudge = row > 0 && row <= contentList.size();
        return columnJudge && rowJudge;
    }

    /**
     *
     * @Title: getColumnIndex
     * @Description: 返回自然列
     * @param strings
     * @param title
     * @return
     */
    private int getColumnIndex(String[] strings, String title)
    {
        int column = 1;
        for (String s : strings)
        {
            if (title.equals(s))
            {
                return column;
            }
            column++;
        }
        return -1;
    }

    @Override
    public List<String[]> read() throws IOException {
        if (reader == null)
        {
            throw new IOException("reader is null, open File first");
        }
        List<String[]> result = read();
        result.remove(0);
        return result;
    }

    @Override
    public void open(Path path) throws IOException {
        isr = new InputStreamReader(new FileInputStream(path.toFile()), charset);
        reader = new CsvListReader(isr, type.getCsvPreference());
    }

    @Override
    public void close(){
        Closee.close(reader,isr);
    }

    @Override
    public String[] readTitle() throws IOException {
        if (reader == null)
        {
            throw new IOException("reader is null, open File first");
        }
        String[] header = reader.getHeader(true);
        return header;
    }

    @Override
    public List<String[]> readContent() throws IOException {
        if (reader == null)
        {
            throw new IOException("reader is null, open File first");
        }
        List<String[]> result = read();
        result.remove(0);
        return result;
    }

    @Override
    public String read(List<String[]> list, String title, int row) {
        int column = getColumnIndex(list.get(0), title);
        if (column != -1)
        {
            return read(list, column, row);
        } else
        {
            logger.error("column(" + column + ") is not exist");
            return null;
        }
    }

    @Override
    public String read(List<String[]> list, int column, int row) {
        if (isAvaliable(list, column, row))
        {
            return list.get(row - 1)[column - 1];
        } else
        {
            logger.error("Value(" + column + "," + row + ") is not exist");
            return null;
        }
    }
}
