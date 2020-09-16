package com.chinatsp.code.checker.impl.testcase;

import com.chinatsp.code.checker.api.BaseChecker;
import com.chinatsp.code.checker.api.IChecker;
import com.chinatsp.code.configure.Configure;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.testcase.TestCase;
import com.chinatsp.dbc.entity.Message;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TestCaseChecker extends BaseChecker implements IChecker {


    @Override
    public void check(Map<String, List<BaseEntity>> map, List<Message> messages, Configure configure) {
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
        // 单独检查test case
        List<BaseEntity> entities = getEntity(map, TestCase.class);
        for (int i = 0; i < entities.size(); i++) {
            int index = i + 1;
            TestCase testCase = (TestCase) entities.get(i);
            String name = testCase.getClass().getName();
            // 检查名字是否符合python命名规范
            checkUtils.checkPythonFunction(testCase.getName(), index, name);
            // 检查操作是否都定义了
            checkUtils.checkAction(testCase.getPreCondition(), index, map);
            checkUtils.checkAction(testCase.getSteps(), index, map);
            checkUtils.checkAction(testCase.getExpect(), index, map);
        }
    }
}
