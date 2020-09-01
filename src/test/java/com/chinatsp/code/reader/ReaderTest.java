package com.chinatsp.code.reader;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lizhe
 * @date 2020/9/1 10:59
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
class ReaderTest {
    @Autowired
    private Reader reader;

    @Test
    void readEntity() {
        Path path = Paths.get("d:\\template.xlsx");
        reader.readEntity(path);
    }
}