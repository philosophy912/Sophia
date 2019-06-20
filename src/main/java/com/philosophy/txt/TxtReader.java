package com.philosophy.txt;


import com.philosophy.api.txt.IReader;
import com.philosophy.tools.Closee;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Setter
public class TxtReader implements IReader<String> {
    private static Logger logger = LogManager.getLogger(TxtReader.class);
    private BufferedReader br;
    private String charset = UTF8;


    @Override
    public List<String> read() throws IOException {
        List<String> result = new ArrayList<>();
        String str;
        while ((str = br.readLine()) != null) {
            result.add(str);
        }
        return result;
    }

    @Override
    public void open(Path path) throws IOException {
        br = new BufferedReader(new InputStreamReader(Files.newInputStream(path), charset));
    }

    @Override
    public void close() {
        Closee.close(br);
    }
}
