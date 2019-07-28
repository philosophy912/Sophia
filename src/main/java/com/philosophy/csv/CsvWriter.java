package com.philosophy.csv;

import com.philosophy.api.csv.ECSVType;
import com.philosophy.api.csv.ICsvWriter;
import com.philosophy.tools.Closee;
import lombok.Setter;
import org.supercsv.io.CsvListWriter;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class CsvWriter implements ICsvWriter<String> {
    @Setter
    private ECSVType type;
    private CsvListWriter csvListWriter;

    public CsvWriter(final Writer writer) {
        csvListWriter = new CsvListWriter(writer, type.getCsvPreference());
    }


    @Override
    public void write(String[] strings) throws IOException {
        checkWriter();
        csvListWriter.write(strings);
    }

    @Override
    public void write(List<String[]> stringList) throws IOException {
        for (String[] s : stringList) {
            write(s);
        }
    }


    @Override
    public void close() {
        Closee.close(csvListWriter);
    }

    private void checkWriter() throws IOException {
        if (csvListWriter == null) {
            throw new IOException("csvListWriter is null, open File first");
        }
    }
}
