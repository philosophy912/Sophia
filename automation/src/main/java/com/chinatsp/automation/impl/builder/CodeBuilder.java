package com.chinatsp.automation.impl.builder;

import com.chinatsp.automation.api.builder.BaseBuilder;
import com.chinatsp.automation.api.builder.IBuilder;
import com.chinatsp.automation.api.builder.TestCaseTypeEnum;
import com.chinatsp.automation.entity.base.BaseEntity;
import com.chinatsp.automation.entity.compare.Function;
import com.philosophy.base.util.FilesUtils;
import com.philosophy.base.util.StringsUtils;
import freemarker.template.Template;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.StringUtil;
import org.springframework.stereotype.Component;

import java.io.Writer;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * @author lizhe
 * @date 2020/5/28 14:38
 **/
@Slf4j
@Component
public class CodeBuilder extends BaseBuilder implements IBuilder {

    private Template template;
    private String fileName;

    private void createTemplate(String templateName, TestCaseTypeEnum type) {
        template = getTemplate(templateName, type);

    }

    @SneakyThrows
    private void createFileName(Path path) {
        fileName = FilesUtils.getFileNameAndExtension(path).getFirst();
    }

    @SneakyThrows
    private void writeToFile(Map<String, Object> map, Path path) {
        Writer out = getWriter(path);
        template.process(map, out);
    }


    public void handleMap(Map<String, Object> map, List<? extends BaseEntity> objects, Function function, Function suite) {
        if (!StringsUtils.isEmpty(projectName)) {
            map.put("projectName", projectName);
        }
        if (!StringsUtils.isEmpty(testCaseName)) {
            map.put("testCaseName", testCaseName);
        }
        if (!StringsUtils.isEmpty(version)) {
            map.put("version", version);
        }
        if (!StringsUtils.isEmpty(automotive)) {
            map.put("automotive", automotive);
        }
        if (objects != null) {
            map.put("objects", objects);
        }
        if (function != null && function.getActions().size() != 0) {
            map.put("functions", function.getActions());
        }
        if (!StringsUtils.isEmpty(socPort)) {
            map.put("socPort", socPort);
        }
        if (suite != null && suite.getSuites().size() != 0) {
            map.put("suites", suite.getSuites());
        }
    }


    @Override
    public void build(String templateName, List<? extends BaseEntity> objects, TestCaseTypeEnum type, Path path, Function function) {
        createTemplate(templateName, type);
        createFileName(path);
        Map<String, Object> map = createMap(fileName);
        handleMap(map, objects, function, null);
        writeToFile(map, path);
    }

    @Override
    public void build(String templateName, List<? extends BaseEntity> objects, TestCaseTypeEnum type, Path path, Function function, Function suite) {
        createTemplate(templateName, type);
        createFileName(path);
        Map<String, Object> map = createMap(fileName);
        handleMap(map, objects, function, suite);
        writeToFile(map, path);
    }

    @Override
    public void build(String templateName, List<? extends BaseEntity> objects, TestCaseTypeEnum type, Path path) {
        createTemplate(templateName, type);
        createFileName(path);
        Map<String, Object> map = createMap(fileName);
        handleMap(map, objects, null, null);
        writeToFile(map, path);
    }

    @Override
    public void build(String templateName, TestCaseTypeEnum type, Path path) {
        createTemplate(templateName, type);
        createFileName(path);
        Map<String, Object> map = createMap(fileName);
        handleMap(map, null, null, null);
        writeToFile(map, path);
    }
}
