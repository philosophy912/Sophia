package com.philosophy.csv;


import com.philosophy.api.csv.ECSVType;
import com.philosophy.api.csv.ICsvWriter;
import com.philosophy.tools.Closee;
import lombok.Setter;
import org.supercsv.io.CsvListWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.List;

@Setter
public class CsvWriter implements ICsvWriter<String>{
    private ECSVType type = ECSVType.COMMA;
    private boolean isAppend = true;
    private String charset = UTF8;

    private CsvListWriter writer;
    private OutputStreamWriter osw;

    public CsvWriter() {
    }

    public CsvWriter(ECSVType type, boolean isAppend, String charset) {
        this.type = type;
        this.isAppend = isAppend;
        this.charset = charset;
    }

    @Override
    public void write(String[] strings) throws IOException {
        if (writer == null) {
            throw new IOException("Writer is null, open writer firstly");
        }
        writer.write(strings);
    }

    @Override
    public void write(List<String[]> stringList) throws IOException {
        for (String[] s : stringList) {
            write(s);
        }
    }

    @Override
    public void open(Path path) throws IOException {
        osw = new OutputStreamWriter(new FileOutputStream(path.toFile(), isAppend), charset);
        writer = new CsvListWriter(osw, type.getCsvPreference());
    }

    @Override
    public void close() {
        Closee.close(writer, osw);
    }
}
