package com.philosophy.io;

import com.philosophy.api.io.IOpenWriter;
import lombok.Setter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @author lizhe
 * @since V1.0.0 2019/7/28 22:01
 **/
@Setter
public class OpenWriter implements IOpenWriter {

    private boolean isAppend;
    private String charset;


    @Override
    public Writer open(Path path) throws IOException {
        if (isAppend) {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            return new OutputStreamWriter(Files.newOutputStream(path, StandardOpenOption.WRITE, StandardOpenOption.APPEND), charset);
        }
        return new OutputStreamWriter(Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE), charset);
    }
}
