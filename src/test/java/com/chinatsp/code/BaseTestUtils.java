package com.chinatsp.code;

import com.philosophy.base.util.FilesUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author lizhe
 * @date 2020/9/9 16:43
 **/
@Slf4j
public class BaseTestUtils {

    public static String getResourceFolder() {
        return FilesUtils.getCurrentPath() + "\\src\\test\\resources";
    }
    public static String getFileFolder() {
        return FilesUtils.getCurrentPath() + "\\file";
    }

    @SneakyThrows
    public static Class getClass(List<String> classes, String className) {
        for (String s : classes) {
            String[] strings = s.split("\\.");
            String name = strings[strings.length - 1];
            if (name.equalsIgnoreCase(className)) {
                return Class.forName(s);
            }
        }
        throw new RuntimeException("error");
    }
}
