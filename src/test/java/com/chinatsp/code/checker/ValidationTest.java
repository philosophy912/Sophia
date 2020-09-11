package com.chinatsp.code.checker;

import com.chinatsp.code.BaseTestUtils;
import com.chinatsp.code.configure.Configure;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.actions.BatteryAction;
import com.chinatsp.code.entity.actions.RelayAction;
import com.chinatsp.code.entity.actions.ScreenOperationAction;
import com.chinatsp.code.entity.collection.Can;
import com.chinatsp.code.entity.compare.CanCompare;
import com.chinatsp.code.entity.compare.ImageCompare;
import com.chinatsp.code.reader.Reader;
import com.chinatsp.dbc.entity.Message;
import com.chinatsp.dbc.impl.DbcParser;
import com.philosophy.base.common.Pair;
import com.philosophy.character.util.CharUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
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


    @Test
    void checkPythonFunction() {
        for (Map.Entry<String, List<BaseEntity>> entry : map.entrySet()) {
            String className = entry.getKey();
            List<BaseEntity> entities = entry.getValue();
            for (int i = 0; i < entities.size(); i++) {
                BaseEntity baseEntity = entities.get(i);
                validation.checkPythonFunction(baseEntity.getName(), i + 1, className);
            }
        }
    }


    @Test
    void checkClickPoints() {
        int width = configure.getMaxWidth();
        int height = configure.getMaxHeight();
        String className = "ScreenOperationAction";
        List<BaseEntity> entities = map.get(CharUtils.lowerCase(className));
        for (int i = 0; i < entities.size(); i++) {
            ScreenOperationAction baseEntity = (ScreenOperationAction) entities.get(i);
            validation.checkClickPoints(baseEntity.getPoints(), i + 1, className, width, height);
        }
    }

    @Test
    void checkClickPositions() {
        int width = configure.getMaxWidth();
        int height = configure.getMaxHeight();
        String className = "ImageCompare";
        List<BaseEntity> entities = map.get(CharUtils.lowerCase(className));
        for (int i = 0; i < entities.size(); i++) {
            ImageCompare baseEntity = (ImageCompare) entities.get(i);
            validation.checkClickPositions(baseEntity.getPositions(), i + 1, className, width, height);
        }
    }

    @Test
    void checkDisplay() {
        int maxDisplay = configure.getMaxDisplay();
        String className = "ScreenOperationAction";
        List<BaseEntity> entities = map.get(CharUtils.lowerCase(className));
        for (int i = 0; i < entities.size(); i++) {
            ScreenOperationAction baseEntity = (ScreenOperationAction) entities.get(i);
            validation.checkDisplay(baseEntity.getScreenIndex(), i + 1, className, maxDisplay);
        }
    }

    @Test
    void checkRelayChannel() {
        int maxDisplay = 8;
        String className = "RelayAction";
        List<BaseEntity> entities = map.get(CharUtils.lowerCase(className));
        for (int i = 0; i < entities.size(); i++) {
            RelayAction baseEntity = (RelayAction) entities.get(i);
            validation.checkRelayChannel(baseEntity.getChannelIndex(), i + 1, className, maxDisplay);
        }
    }

    @Test
    void checkBatteryOperator() {
        String className = "BatteryAction";
        List<BaseEntity> entities = map.get(CharUtils.lowerCase(className));
        for (int i = 0; i < entities.size(); i++) {
            BatteryAction baseEntity = (BatteryAction) entities.get(i);
            Double[] doubles = baseEntity.getValues();
            if (doubles != null) {
                validation.checkBatteryOperator(doubles, i + 1, className, 0, 20);
            } else {
                String file = baseEntity.getCurveFile();
                if (Strings.isEmpty(file)) {
                    throw new RuntimeException("voltage not set and curve file not set");
                }
            }
        }
    }

    @Test
    void checkSignals() {
        String className = "Can";
        List<BaseEntity> entities = map.get(CharUtils.lowerCase(className));
        for (int i = 0; i < entities.size(); i++) {
            Can baseEntity = (Can) entities.get(i);
            validation.checkSignals(baseEntity.getSignals(), messages, i + 1, className);
        }
    }


    @Test
    void checkMessageId() {
        String className = "CanCompare";
        List<BaseEntity> entities = map.get(CharUtils.lowerCase(className));
        for (int i = 0; i < entities.size(); i++) {
            CanCompare baseEntity = (CanCompare) entities.get(i);
            validation.checkMessageId(baseEntity.getMessageId(), baseEntity.getSignalName(), baseEntity.getExpectValue(), messages, i + 1, className);
        }
    }

    @Test
    void checkSimilarity() {
        String className = "ImageCompare";
        List<BaseEntity> entities = map.get(CharUtils.lowerCase(className));
        for (int i = 0; i < entities.size(); i++) {
            ImageCompare baseEntity = (ImageCompare) entities.get(i);
            validation.checkSimilarity(baseEntity.getSimilarity(), i + 1, className);
        }
    }

    @Test
    void checkConfigure() {
        validation.checkConfigure(configure);
    }
}