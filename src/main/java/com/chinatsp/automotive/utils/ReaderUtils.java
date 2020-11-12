package com.chinatsp.automotive.utils;

import com.philosophy.base.util.ClazzUtils;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.chinatsp.automotive.utils.Constant.SPLIT_POINT;

/**
 * @author lizhe
 * @date 2020/9/14 12:45
 **/
@Component
public class ReaderUtils {

    /**
     * 根据sheet名字获取全包名
     *
     * @param sheetName sheet的名字
     * @return sheet对应的实体类的全包名
     */
    public String getFullClassName(String sheetName, String packageName) {
        List<String> classes = ClazzUtils.getClazzName(packageName, true);
        for (String className : classes) {
            String[] names = className.split(SPLIT_POINT);
            String name = names[names.length - 1];
            if (name.equalsIgnoreCase(sheetName)) {
                return className;
            }
        }
        String error = "Not found " + sheetName + " in " + packageName;
        throw new RuntimeException(error);
    }
}
