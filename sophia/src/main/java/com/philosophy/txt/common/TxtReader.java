package com.philosophy.txt.common;


import com.philosophy.base.api.IReader;
import com.philosophy.base.common.Closee;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lizhe
 */
@Slf4j
public class TxtReader implements IReader<String> {
    @Setter
    private BufferedReader br;
    @Setter
    private boolean skipBlankLine;

    public TxtReader(final Reader reader, boolean skipBlankLine) {
        br = new BufferedReader(reader);
        this.skipBlankLine = skipBlankLine;
    }

    @Override
    public List<String> read() throws IOException {
        List<String> result = new LinkedList<>();
        String str;
        while ((str = br.readLine()) != null) {
            if (!("".equals(str.trim()) && skipBlankLine)){
                result.add(str);
            }
        }
        return result;
    }

    @Override
    public void close() {
        Closee.close(br);
    }

}
