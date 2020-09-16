package com.chinatsp.code.service;

import com.chinatsp.code.service.api.ITemplateService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author lizhe
 * @date 2020/9/16 10:43
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class TemplateServiceTest {

    @Autowired
    private ITemplateService templateService;


    @Test
    void createTemplate() {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhssmm"));
        Path path = Paths.get("template" + time + ".xlsx");
        templateService.createTemplate(path);
    }
}