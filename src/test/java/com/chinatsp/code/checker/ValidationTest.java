package com.chinatsp.code.checker;

import com.chinatsp.code.BaseTestUtils;
import com.chinatsp.code.configure.Configure;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.reader.Reader;
import com.chinatsp.dbc.entity.Message;
import com.chinatsp.dbc.impl.DbcParser;
import com.philosophy.base.common.Pair;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
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
    @Autowired
    private DbcParser dbcParser;


    private Map<String, List<BaseEntity>> map;
    private Configure configure;
    private List<Message> messages;

    @BeforeEach
    void setup() {
        Path path = Paths.get(BaseTestUtils.getFileFolder(), "template.xlsx");
        Pair<Map<String, List<BaseEntity>>, Configure> pair = reader.readTestCase(path);
        map = pair.getFirst();
        configure = pair.getSecond();
        Path dbc = Paths.get(configure.getDbcFile());
        messages = dbcParser.parse(dbc);
    }



}