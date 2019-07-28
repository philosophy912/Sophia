package com.philosophy.api.io;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;

/**
 * @author lizhe
 * @since V1.0.0 2019/7/28 21:58
 **/
public interface IOpenWriter {

    Writer open(Path path) throws IOException;

}
