package com.chinatsp.automotive.template;

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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * @author lizhe
 * @date 2020-08-31 21:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class TemplateTest {
    @Autowired
    private TestCaseTemplate template;


    @SneakyThrows
    @Test
    void createTemplateExcelFile() {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhssmm"));
        Path path = Paths.get("template" + time + ".xlsx");
        if (Files.exists(path)) {
            FilesUtils.deleteFiles(path);
        }
        template.createTemplateExcelFile(path);
    }
}