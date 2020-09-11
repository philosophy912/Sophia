package com.chinatsp.code.checker;

import com.chinatsp.code.BaseTestUtils;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.reader.Reader;
import com.philosophy.base.util.ClazzUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.chinatsp.code.utils.Constant.PACKAGE_NAME;

/**
 * @author lizhe
 * @date 2020/9/10 17:24
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class ValidationTest {

    @Autowired
    private Validation validation;
    @Autowired
    private Reader reader;

    private Map<String, List<BaseEntity>> map;
    private List<String> classes;

    @BeforeEach
    void setup() {
        classes = ClazzUtils.getClazzName(PACKAGE_NAME, true);
        Path path = Paths.get(BaseTestUtils.getFileFolder(), "template.xlsx");
        map = reader.readEntity(path);
    }


    @Test
    void checkPythonFunction() {
        for (Map.Entry<String, List<BaseEntity>> entry : map.entrySet()) {
            String className = entry.getKey();
            Class<?> clazz = BaseTestUtils.getClass(classes, className);
            List<BaseEntity> entities = entry.getValue();
            entities.forEach(baseEntity -> {
                log.info("object = {}", clazz.cast(baseEntity));
            });
        }
    }

    @Test
    void checkClickPoint() {
    }

    @Test
    void checkDisplay() {
    }

    @Test
    void checkRelayChannel() {
    }

    @Test
    void checkBatteryOperator() {
    }

    @Test
    void checkSignalNameAndValue() {
    }

    @Test
    void checkMessageId() {
    }

    @Test
    void checkSimilarity() {
    }
}