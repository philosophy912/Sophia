package com.chinatsp.automotive.reader;

import com.chinatsp.automotive.BaseTestUtils;
import com.chinatsp.automotive.entity.BaseEntity;
import com.chinatsp.automotive.enumeration.ConfigureTypeEnum;
import com.philosophy.base.common.Pair;
import com.philosophy.base.util.ClazzUtils;
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

import static com.chinatsp.automotive.utils.Constant.PACKAGE_NAME;

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



    @Test
    void readTestCase() {
        List<String> classes = ClazzUtils.getClazzName(PACKAGE_NAME, true);
        Path path = Paths.get(BaseTestUtils.getFileFolder(), "template3S1.xlsx");
        Pair<Map<String, List<BaseEntity>>, Map<ConfigureTypeEnum, String>> pair = reader.readTestCase(path);
        for (Map.Entry<String, List<BaseEntity>> entry : pair.getFirst().entrySet()) {
            String className = entry.getKey();
            Class<?> clazz = BaseTestUtils.getClass(classes, className);
            List<BaseEntity> entities = entry.getValue();
            entities.forEach(baseEntity -> log.info("object = {}", clazz.cast(baseEntity)));
        }
    }
}