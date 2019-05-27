package com.philosophy.api.csv;

import com.philosophy.api.txt.IReader;

import java.io.IOException;
import java.util.List;

public interface ICsvReader<T> extends IReader<T> {

    T readTitle() throws IOException;

    List<T> readContent() throws IOException;

    String read(List<T> list, String title, int row);

    String read(List<T> list, int column, int row);
}
