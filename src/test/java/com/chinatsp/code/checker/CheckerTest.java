package com.chinatsp.code.checker;

import com.chinatsp.code.BaseTestUtils;
import com.chinatsp.code.configure.Configure;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.reader.Reader;
import com.chinatsp.dbc.entity.Message;
import com.chinatsp.dbc.impl.DbcParser;
import com.philosophy.base.common.Pair;
import com.philosophy.base.util.ClazzUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
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

import static com.chinatsp.code.utils.Constant.PACKAGE_NAME;
import static org.junit.jupiter.api.Assertions.*;

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
    @Autowired
    private DbcParser dbcParser;

    private Map<String, List<BaseEntity>> map;

    private Configure configure;

    private List<Message> messages;

    @BeforeEach
    void beforeTest() {
        Path dbc = Paths.get(BaseTestUtils.getFileFolder(), "HiFire_B31CP_Info_HU_CAN_V2.0.dbc");
        messages = dbcParser.parse(dbc);
        Path path = Paths.get(BaseTestUtils.getFileFolder(), "template.xlsx");
        Pair<Map<String, List<BaseEntity>>, Configure> pair = reader.readTestCase(path);
        map = pair.getFirst();
        configure = pair.getSecond();
    }


    @Test
    void check() {
        checker.check(map, messages, configure);
    }
}