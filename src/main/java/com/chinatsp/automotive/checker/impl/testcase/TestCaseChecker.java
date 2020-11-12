package com.chinatsp.automotive.checker.impl.testcase;

import com.chinatsp.automotive.checker.api.BaseChecker;
import com.chinatsp.automotive.checker.api.IChecker;
import com.chinatsp.automotive.entity.BaseEntity;
import com.chinatsp.automotive.entity.testcase.TestCase;
import com.chinatsp.automotive.enumeration.ConfigureTypeEnum;
import com.chinatsp.dbc.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class TestCaseChecker extends BaseChecker implements IChecker {

    private void checkAllFunctionDuplicate(Map<String, List<BaseEntity>> map){
        // 检查TestCase中的是否有重复的情况发生
        Map<String, BaseEntity> duplicateMap = new HashMap<>(12);
        for (Map.Entry<String, List<BaseEntity>> entry : map.entrySet()) {
            String entityName = entry.getKey();
            List<BaseEntity> entities = entry.getValue();
            if (!entityName.equalsIgnoreCase("testcase")) {
                for (int i = 0; i < entities.size(); i++) {
                    int index = i + 1;
                    BaseEntity baseEntity = entities.get(i);
                    String functionName = baseEntity.getName();
                    if (duplicateMap.containsKey(functionName)) {
                        String error = "Sheet[" + entityName + "]的第" + index + "行的函数名" + functionName + "有重复，请检查";
                        throw new RuntimeException(error);
                    } else {
                        duplicateMap.put(functionName, baseEntity);
                    }
                }
            }
        }
    }


    @Override
    public void check(Map<String, List<BaseEntity>> map, List<Message> messages, Map<ConfigureTypeEnum, String> configure) {
        // checkAllFunctionDuplicate(map);
        // 单独检查test case
        List<BaseEntity> entities = getEntity(map, TestCase.class);
        for (int i = 0; i < entities.size(); i++) {
            int index = i + 1;
            TestCase testCase = (TestCase) entities.get(i);
            String name = testCase.getClass().getName();
            // 检查名字是否符合python命名规范
            checkUtils.checkPythonFunction(testCase.getName(), index, name);
            log.debug("{} times check preCondition", index);
            // 检查操作是否都定义了
            checkUtils.checkAction(testCase.getPreCondition(), index, map);
            log.debug("{} times check steps", index);
            checkUtils.checkAction(testCase.getSteps(), index, map);
            log.debug("{} times check expect", index);
            checkUtils.checkAction(testCase.getExpect(), index, map);
        }
    }
}
