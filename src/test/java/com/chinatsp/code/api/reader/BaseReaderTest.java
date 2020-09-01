package com.chinatsp.code.api.reader;

import com.chinatsp.code.Entity;
import com.chinatsp.code.enumeration.BatteryOperationTypeEnum;
import com.chinatsp.code.enumeration.ElementAttributeEnum;
import com.chinatsp.code.utils.Constant;
import com.philosophy.base.common.Pair;
import com.philosophy.base.util.ClazzUtils;
import com.philosophy.excel.utils.ExcelUtils;
import lombok.SneakyThrows;
import org.apache.commons.lang3.EnumUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
                System.out.println(field.getName() + " : " + field.getType().getName() + " : " + field.getType().isEnum());
                if (field.getType() == List.class) {
                    Type genericType = field.getGenericType();
                    System.out.println(field.getName() + " : " + field.getType().getName() + " : " + genericType.getTypeName());
                }
            }
        }
    }
    @SneakyThrows
    @Test
    void testCase(){
        Entity entity = new Entity();
        Field[] fields = Entity.class.getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            if(field.getType().isEnum()){
                Method method = field.getType().getMethod("fromValue", String.class);
                //field.set(entity, Enum.valueOf(null, "设置电压"));
                field.set(entity, method.invoke(null, "设置电压"));
            }
        }
        System.out.println(entity);


//        Class<?> clazz = BatteryOperationTypeEnum.class;
//        Method method = clazz.getMethod("fromValue", String.class);
//        BatteryOperationTypeEnum o = (BatteryOperationTypeEnum) method.invoke(clazz, "设置电压");
//        System.out.println(o.getValue());

    }
}