package com.chinatsp.code.writer;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.collection.Element;
import com.chinatsp.code.enumeration.ConfigureTypeEnum;
import com.chinatsp.code.utils.WriterUtils;
import com.philosophy.base.common.Pair;
import com.philosophy.base.common.Triple;
import com.philosophy.base.util.FilesUtils;
import com.philosophy.character.util.CharUtils;
import freemarker.template.Template;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lizhe
 * @date 2020/9/28 11:03
 **/
@Component
public class FreeMarkerWriter extends BaseWriter {

    private List<Triple<String, String, String>> convert(Map<ConfigureTypeEnum, String> configureMap) {
        List<Triple<String, String, String>> pairs = new ArrayList<>();
        for (Map.Entry<ConfigureTypeEnum, String> entry : configureMap.entrySet()) {
            ConfigureTypeEnum type = entry.getKey();
            String comment = type.getValue();
            String functionName = type.getName();
            String values = entry.getValue();
            pairs.add(new Triple<>(comment, functionName, values));
        }
        return pairs;
    }

    private String getClassName(Class<?> clazz){
        return CharUtils.lowerCase(clazz.getSimpleName());
    }


    @SneakyThrows
    public void writeConfigure(Map<ConfigureTypeEnum, String> configureMap, Path configurePath) {
        Template template = getTemplate("config");
        String fileName = FilesUtils.getFileNameAndExtension(configurePath).getFirst();
        Map<String, Object> map = createMap(fileName);
        List<Triple<String, String, String>> pairs = convert(configureMap);
        map.put("configure", pairs);
        writeToFile(template, map, configurePath);
    }

    @SneakyThrows
    public void writeEntity(Map<String, List<BaseEntity>> entityMap, Path contextPath) {
        Template template = getTemplate("context");
        String fileName = FilesUtils.getFileNameAndExtension(contextPath).getFirst();
        Map<String, Object> map = createMap(fileName);
        String name = getClassName(Element.class);
        List<BaseEntity> elements = entityMap.get(name);
        map.put(name, writerUtils.convertElements(elements));
        writeToFile(template, map, contextPath);
    }

}
