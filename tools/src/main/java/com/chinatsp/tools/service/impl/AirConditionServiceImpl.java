package com.chinatsp.tools.service.impl;

import com.chinatsp.automation.api.builder.TestCaseTypeEnum;
import com.chinatsp.automation.entity.actions.CanAction;
import com.chinatsp.automation.entity.actions.ScreenAction;
import com.chinatsp.automation.entity.base.TestCase;
import com.chinatsp.automation.entity.compare.Function;
import com.chinatsp.automation.entity.testcase.Receive;
import com.chinatsp.automation.entity.testcase.Send;
import com.chinatsp.dbc.entity.Message;
import com.chinatsp.tools.service.api.IService;
import com.chinatsp.tools.service.api.TestCaseService;
import com.philosophy.base.common.Pair;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


import static com.chinatsp.tools.config.IConstant.CAN;
import static com.chinatsp.tools.config.IConstant.TESTCASE;
import static com.chinatsp.tools.config.IConstant.TEST_CASES;


/**
 * @author lizhe
 * @date 2020/6/4 9:08
 **/
@Slf4j
@Component
public class AirConditionServiceImpl extends TestCaseService implements IService {

    private static final TestCaseTypeEnum AIR_CONDITION = TestCaseTypeEnum.AIR_CONDITION;


    @Override
    public void generator() {
        Pair<Map<String, Path>, List<Message>> pair = generatorDbc(configure.getProjectName().toLowerCase(), AIR_CONDITION);
        Map<String, Path> map = pair.getFirst();
        List<Message> messages = pair.getSecond();
        generatorTestCases(map.get(TESTCASE), messages, map);
        generatorInstall(map);
    }

    /**
     * 生成测试用例
     * 主要是读取excel文件中的相关内容 测试用例/屏幕测试用例/动作/屏幕操作
     * @param testcase excel文件路径
     * @param messages CAN消息列表
     * @param map 存放文件的路径
     */
    @SneakyThrows
    private void generatorTestCases(Path testcase, List<Message> messages, Map<String, Path> map) {
        Workbook workbook = excelUtils.openWorkbook(testcase);
        Sheet sheet = readerUtils.getSpecificSheet(workbook, DISPLAY_INDEX, EXPECT_FUNCTIONS);
        List<Receive> receives = receiverReader.read(sheet);
        sheet = readerUtils.getSpecificSheet(workbook, CAN_COMPARE, PRECONDITION_FUNCTIONS, STEPS_FUNCTIONS);
        List<Send> sends = senderReader.read(sheet);
        sheet = readerUtils.getSpecificSheet(workbook, DEPENDENCY);
        List<CanAction> canActions = canActionsReader.read(sheet);
        sheet = readerUtils.getSpecificSheet(workbook, POSITION, CONTINUE_TIME);
        List<ScreenAction> screenActions = screenActionsReader.read(sheet);
        excelUtils.close(workbook);
        sheet = readerUtils.getSpecificSheet(workbook, ACTIONS);
        List<Function> functions = functionReader.read(sheet);
        excelUtils.close(workbook);
        // 校验测试用例文件
        int width = configure.getWidth();
        int height = configure.getHeight();
        receiverCheck.check(receives, canActions, screenActions);
        senderCheck.check(sends, canActions, screenActions, messages);
        canActionsCheck.check(canActions, messages);
        screenActionsCheck.check(screenActions, width, height);
        functionCheck.check(functions, canActions, screenActions);
        // 处理测试用例部分
        generatorCluster(receives, map, AIR_CONDITION, functions);
        generatorSend(sends, map, functions);
        generatorTools(map, AIR_CONDITION);
        generatorCan(canActions, map, screenActions);
    }

    /**
     * 生成 Send类别即测试用例发送CAN消息部分的用例
     * @param sends 发送CAN消息的测试用例内容
     * @param map  测试用例存放路径，其实只需要获取TEST_CASES的路径
     * @param functions 前置条件的function
     */
    private void generatorSend(List<Send> sends, Map<String, Path> map, List<Function> functions) {
        Map<String, List<? extends TestCase>> sendMap = getCategoryList(sends);
        Map<String, Function> functionMap = handleFunction(functions);
        // 处理测试用例部分
        for (Map.Entry<String, List<? extends TestCase>> entry : sendMap.entrySet()) {
            // 需要针对compare写入python和json文件，其中json文件写入到当前路径下，而python文件写入到config下
            String category = entry.getKey().toLowerCase();
            Function suite = getSuite(functions, category);
            Function function = getAndCheckFunction(functionMap, category);
            List<? extends TestCase> entities = entry.getValue();
            Path autoTestcase = Paths.get(map.get(TEST_CASES).toString(), "test_" + category + ".py");
            builder.setTestCaseName(category);
            builder.build(TESTCASE, entities, AIR_CONDITION, autoTestcase, function, suite);
        }
        // 设置完成后清空
        builder.setTestCaseName(null);
    }

    /**
     * 生成CAN操作的用例
     * @param canActions can操作
     * @param map 存放路径
     * @param screenActions 屏幕操作用例
     */
    private void generatorCan(List<CanAction> canActions, Map<String, Path> map, List<ScreenAction> screenActions) {
        String base = map.get(CAN).toString();
        generatorCan(canActions, map, AIR_CONDITION);
        Path screenActionsPath = Paths.get(base, SCREEN_ACTIONS + ".py");
        builder.build(SCREEN_ACTIONS, screenActions, AIR_CONDITION, screenActionsPath);
        // 设置完成后清空
        builder.setTestCaseName(null);
    }

}
