package com.philosophy.csv.common;

import com.philosophy.base.common.Closee;
import com.philosophy.csv.api.CsvEnum;
import com.philosophy.csv.api.ICsvWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * @author lizhe
 * @date 2019/10/16:13:21
 */
public class CsvWriter implements ICsvWriter<String> {

    private Writer writer;
    private CSVPrinter csvPrinter;
    private CSVFormat csvFormat;

    public CsvWriter(Writer writer, CsvEnum csvEnum) {
        this.writer = writer;
        csvFormat = csvEnum.getCSVFormat().withRecordSeparator("\r\n");
    }


    @Override
    public void write(String[] contents) throws IOException {
        csvPrinter = new CSVPrinter(writer, csvFormat);
        csvPrinter.printRecord(contents);
    }

    @Override
    public void write(List<String[]> contents) throws IOException {
        csvPrinter = new CSVPrinter(writer, csvFormat);
        for(String[] content: contents){
            csvPrinter.printRecord(content);
        }
    }

    @Override
    public void close() throws Exception {
        Closee.close(writer, csvPrinter);
    }
}
