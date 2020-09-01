package com.chinatsp.code.api.reader;


import com.chinatsp.code.utils.Constant;
import com.philosophy.base.util.ClazzUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;


/**
 * @author lizhe
 * @date 2020/8/18 13:42
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
class BaseReaderTest {


    @SneakyThrows
    @Test
    void setEntity() {
        List<String> classes = ClazzUtils.getClazzName(Constant.PACKAGE_NAME, true);
        for (String name : classes) {
            Class<?> clazz = Class.forName(name);
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                // System.out.println(field.getName() + " : " + field.getType().getName() + " : " + field.getType().isEnum());
                if (field.getType() == List.class) {
                    Type genericType = field.getGenericType();
                    String genericTypeTypeName = genericType.getTypeName();
                    genericTypeTypeName = genericTypeTypeName.replace("java.util.List", "");
                    // System.out.println(field.getName() + " : " + field.getType().getName() + " : " + genericType.getTypeName());
                    System.out.println(field.getName() + " : " + field.getType().getName() + " : " + genericTypeTypeName);

                }
            }
        }
    }
}