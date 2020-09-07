package com.chinatsp.code.template;

import com.philosophy.base.util.FilesUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Files;
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

    @SneakyThrows
    @Test
    void createTemplateExcelFile() {
        Path path = Paths.get("template.xlsx");
        if(Files.exists(path)){
            FilesUtils.deleteFiles(path);
        }
        template.createTemplateExcelFile(path);
    }
}