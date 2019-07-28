package com.philosophy.txt;


import com.philosophy.api.txt.IReader;
import com.philosophy.tools.Closee;
import lombok.Setter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Setter
public class TxtReader implements IReader<String> {
    private BufferedReader br;

    public TxtReader(final Reader reader) {
        br = new BufferedReader(reader);
    }

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
    public void close() {
        Closee.close(br);
    }
}
