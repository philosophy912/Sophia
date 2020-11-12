package com.chinatsp.automotive.service;

import com.chinatsp.automotive.service.api.ITemplateService;
import com.chinatsp.automotive.template.TestCaseTemplate;
import com.philosophy.base.util.FilesUtils;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author lizhe
 * @date 2020/9/16 10:39
 **/
@Service
public class TemplateService implements ITemplateService {
    @Resource
    private TestCaseTemplate template;


    @SneakyThrows
    public void createTemplate(Path path) {
        Path file = Paths.get(path.toAbsolutePath().toString(), "template.xls");
        if (Files.exists(file)) {
            FilesUtils.deleteFiles(file);
        }
        template.createTemplateExcelFile(file);
    }
}
