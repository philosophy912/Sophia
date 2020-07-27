package com.chinatsp.tools.service.impl;

import com.chinatsp.automation.api.builder.TestCaseTypeEnum;
import com.chinatsp.automation.entity.actions.CanAction;
import com.chinatsp.automation.entity.compare.Function;
import com.chinatsp.automation.entity.testcase.Cluster;
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
import java.util.List;
import java.util.Map;

import static com.chinatsp.tools.config.IConstant.TESTCASE;


/**
 * @author lizhe
 * @date 2020/6/4 9:10
 **/
@Slf4j
@Component
public class ClusterServiceImpl extends TestCaseService implements IService {

    private static final TestCaseTypeEnum CLUSTER = TestCaseTypeEnum.CLUSTER;

    @Override
    public void generator() {
        String projectName = configure.getProjectName().toLowerCase();
        Pair<Map<String, Path>, List<Message>> pair = generatorDbc(projectName, CLUSTER);
        Map<String, Path> map = pair.getFirst();
        List<Message> messages = pair.getSecond();
        generatorTestCases(map.get(TESTCASE), messages, map);
        generatorInstall(map);
    }

    /**
     * 生成测试用例
     * 主要是读取excel文件中的相关内容 测试用例/动作
     * @param testcase excel文件路径
     * @param messages CAN消息列表
     * @param map 存放文件的路径
     */
    @SneakyThrows
    private void generatorTestCases(Path testcase, List<Message> messages, Map<String, Path> map) {
        Workbook workbook = excelUtils.openWorkbook(testcase);
        Sheet sheet = readerUtils.getSpecificSheet(workbook, POSITION, EXPECT_FUNCTIONS);
        List<Cluster> clusters = clusterReader.read(sheet);
        sheet = readerUtils.getSpecificSheet(workbook, DEPENDENCY);
        List<CanAction> canActions = canActionsReader.read(sheet);
        excelUtils.close(workbook);
        sheet = readerUtils.getSpecificSheet(workbook, ACTIONS);
        List<Function> functions = functionReader.read(sheet);
        excelUtils.close(workbook);
        // 校验测试用例文件
        clusterCheck.check(clusters, canActions);
        canActionsCheck.check(canActions, messages);
        functionCheck.check(functions, canActions, null);
        // 处理测试用例部分
        generatorCluster(clusters, map, CLUSTER, functions);
        generatorTools(map, CLUSTER);
        generatorCan(canActions, map, CLUSTER);
    }
}
