package com.philosophy.api.io;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;

/**
 * @author lizhe
 * @since V1.0.0 2019/7/28 21:58
 **/
public interface IOpenReader {

    Reader open(Path path) throws IOException;
}
