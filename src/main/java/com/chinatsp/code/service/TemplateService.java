package com.chinatsp.code.service;

import com.chinatsp.code.service.api.ITemplateService;
import com.chinatsp.code.template.TestCaseTemplate;
import com.philosophy.base.util.FilesUtils;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.file.Files;
import java.nio.file.Path;

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
        String absolutePath = path.toAbsolutePath().toString();
        if (absolutePath.endsWith("xls") || absolutePath.endsWith("xlsx")) {
            if (Files.exists(path)) {
                FilesUtils.deleteFiles(path);
            }
            template.createTemplateExcelFile(path);
        } else {
            String error = "文件需要是Excel文件";
            throw new RuntimeException(error);
        }

    }
}
