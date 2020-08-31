package com.chinatsp.code.template;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * @author lizhe
 * @date 2020-08-31 21:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class TemplateTest {
    @Autowired
    private Template template;

    @Test
    void createTemplateExcelFile() {
        Path path = Paths.get("d:\\template.xlsx");
        template.createTemplateExcelFile(path);
    }
}