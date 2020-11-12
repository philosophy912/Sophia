package com.chinatsp.automotive.checker;

import com.chinatsp.automotive.BaseTestUtils;
import com.chinatsp.automotive.entity.BaseEntity;
import com.chinatsp.automotive.enumeration.ConfigureTypeEnum;
import com.chinatsp.automotive.reader.Reader;
import com.chinatsp.dbc.entity.Message;
import com.philosophy.base.common.Pair;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


/**
 * @author lizhe
 * @date 2020/9/14 12:52
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class CheckerTest {
    @Autowired
    private Reader reader;
    @Autowired
    private Checker checker;

    private Map<String, List<BaseEntity>> map;

    private Map<ConfigureTypeEnum, String> configure;

    private List<Message> messages;

    @BeforeEach
    void beforeTest() {
        Path path = Paths.get(BaseTestUtils.getFileFolder(), "template.xlsx");
        Pair<Map<String, List<BaseEntity>>, Map<ConfigureTypeEnum, String>> pair = reader.readTestCase(path);
        map = pair.getFirst();
        configure = pair.getSecond();
    }


    @Test
    void check() {
        Path path = Paths.get(BaseTestUtils.getFileFolder(), "template.xlsx");
        checker.check(map, configure, path.getParent().toAbsolutePath().toString());
    }
}