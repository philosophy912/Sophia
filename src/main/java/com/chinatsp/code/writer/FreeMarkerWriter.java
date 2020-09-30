package com.chinatsp.code.writer;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.enumeration.ConfigureTypeEnum;
import com.chinatsp.code.utils.ReaderUtils;
import com.philosophy.base.common.Triple;
import com.philosophy.base.util.FilesUtils;
import com.philosophy.character.util.CharUtils;
import freemarker.template.Template;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.chinatsp.code.utils.Constant.WRITER_PACKAGE_NAME;


/**
 * @author lizhe
 * @date 2020/9/28 11:03
 **/
@Component
public class FreeMarkerWriter extends BaseWriter {
    @Resource
    private ReaderUtils readerUtils;

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
        for (Map.Entry<String, List<BaseEntity>> entry : entityMap.entrySet()) {
            String name = entry.getKey();
            List<BaseEntity> entities = entry.getValue();
            try {
                String fullName = readerUtils.getFullClassName(CharUtils.upperCase(name) + "Writer", WRITER_PACKAGE_NAME);
                Class<?> clazz = Class.forName(fullName);
                Object object = clazz.newInstance();
                Method method = clazz.getDeclaredMethod("convert", List.class);
                Object o = method.invoke(object, entities);
                map.put(name, o);
            } catch (RuntimeException ignored) {
            }
        }
        writeToFile(template, map, contextPath);
    }

}
