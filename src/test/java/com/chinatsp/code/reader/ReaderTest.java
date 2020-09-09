package com.chinatsp.code.reader;

import com.chinatsp.code.BaseTestUtils;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.testcase.TestCase;
import com.philosophy.base.util.ClazzUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static com.chinatsp.code.utils.Constant.PACKAGE_NAME;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lizhe
 * @date 2020/9/1 10:59
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class ReaderTest {
    @Autowired
    private Reader reader;

    @SneakyThrows
    private Class getClass(List<String> classes, String className) {
        for (String s : classes) {
            String[] strings = s.split("\\.");
            String name = strings[strings.length - 1];
            if (name.equalsIgnoreCase(className)) {
                return Class.forName(s);
            }
        }
        throw new RuntimeException("error");
    }

    @Test
    void readEntity() {
        List<String> classes = ClazzUtils.getClazzName(PACKAGE_NAME, true);
        Path path = Paths.get(BaseTestUtils.getFileFolder(), "template.xlsx");
        Map<String, List<BaseEntity>> map = reader.readEntity(path);
        for (Map.Entry<String, List<BaseEntity>> entry : map.entrySet()) {
            String className = entry.getKey();
            Class<?> clazz = getClass(classes, className);
            List<BaseEntity> entities = entry.getValue();
            entities.forEach(baseEntity -> {
                log.info("object = {}", clazz.cast(baseEntity));
            });

        }
//        List<BaseEntity> testcases = map.get("testCase");
//        testcases.forEach(baseEntity -> {
//            System.out.println((TestCase)baseEntity);
//        });
    }


}