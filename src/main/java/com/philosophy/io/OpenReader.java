package com.philosophy.io;

import com.philosophy.api.io.IOpenReader;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author lizhe
 * @since V1.0.0 2019/7/28 21:55
 **/
@Setter
public class OpenReader implements IOpenReader {

    private String charset;

    @Override
    public Reader open(Path path) throws IOException {
        return new InputStreamReader(Files.newInputStream(path), charset);
    }
}
