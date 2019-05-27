package com.philosophy.api.io;

import com.philosophy.api.IConst;

import java.io.IOException;
import java.nio.file.Path;

public interface IO extends IConst {
    /**
     * open file
     * @param path
     * @return
     */
    void open(Path path) throws IOException;

    /**
     * close stream
     * @throws IOException
     */
    void close() throws IOException;
}
