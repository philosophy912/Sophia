package com.chinatsp.tools.service.api;

import com.alibaba.fastjson.JSON;
import com.chinatsp.automation.api.builder.TestCaseTypeEnum;
import com.chinatsp.automation.entity.actions.CanAction;
import com.chinatsp.automation.entity.base.TestCase;
import com.chinatsp.automation.entity.compare.Compare;
import com.chinatsp.automation.entity.compare.Function;
import com.chinatsp.automation.entity.testcase.Cluster;
import com.chinatsp.dbc.entity.Message;
import com.philosophy.base.common.Pair;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.chinatsp.automation.api.IConstant.ACTION;
import static com.chinatsp.automation.api.IConstant.CONDITION;
import static com.chinatsp.automation.api.IConstant.DBC;
import static com.chinatsp.dbc.api.IConstant.UTF8;
import static com.chinatsp.tools.config.IConstant.TESTCASE;
import static com.chinatsp.tools.config.IConstant.TEST_ACTIONS;
import static com.chinatsp.tools.config.IConstant.TEST_CASES;
import static com.chinatsp.tools.config.IConstant.TEST_COMPARES;
import static com.chinatsp.tools.service.api.IService.ACTIONS;
import static com.chinatsp.tools.service.api.IService.ACTION_TESTCASE;
import static com.chinatsp.tools.service.api.IService.COMPARE_TESTCASE;
import static com.chinatsp.tools.service.api.IService.CONDITIONS;
import static com.chinatsp.tools.service.api.IService.DIRECTOR;
import static com.chinatsp.tools.service.api.IService.IMAGE_COMPARE;
import static com.chinatsp.tools.service.api.IService.IMAGE_PROPERTY;
import static com.chinatsp.tools.service.api.IService.SCREEN_SHOT;
import static com.chinatsp.tools.service.api.IService.SERVICE;
import static com.chinatsp.tools.config.IConstant.CAN;
import static com.chinatsp.tools.config.IConstant.CONFIG;
import static com.chinatsp.tools.config.IConstant.JSONS;
import static com.chinatsp.tools.config.IConstant.TOOLS;
import static com.chinatsp.tools.config.IConstant.TOP;

/**
 * @author lizhe
 * @date 2020/6/5 11:27
 **/
@Slf4j
public class TestCaseService extends BaseService {


    protected Pair<Map<String, Path>, List<Message>> generatorDbc(String projectName, TestCaseTypeEnum type) {
        toolUtils.deleteFolder(CURRENT_PATH + File.separator + TOP + projectName);
        Map<String, Path> map = init(projectName, type);
        List<Message> messages = getMessage(map.get(DBC));
        writeMessages(messages, map, projectName);
        return new Pair<>(map, messages);
    }

    /**
     * 创建文件夹
     *
     * @param projectName 项目名
     * @param type        类型
     * @return 键值对 文件名 - Path
     */
    private Map<String, Path> init(String projectName, TestCaseTypeEnum type) {
        Map<String, Path> map = new HashMap<>(20);
        map.putAll(initInputFiles());
        map.putAll(toolUtils.createDirectories(CURRENT_PATH, projectName, type));
        return map;
    }

    @SneakyThrows
    private void writeMessages(List<Message> messages, Map<String, Path> map, String projectName) {
        Path jsonPath = Paths.get(map.get(JSONS).toString(), projectName + ".json");
        Path pythonPath = Paths.get(map.get(CONFIG).toString(), projectName + ".py");
        String jsonStr = JSON.toJSONString(messages);
        String pythonStr = convertPython(jsonStr, "messages = ");
        txtUtils.write(jsonPath, jsonStr, UTF8, false, false);
        txtUtils.write(pythonPath, pythonStr, UTF8, false, false);
    }

    /**
     * 生成actions.py, conditions.py, director.py, service.py文件
     *
     * @param canActions sheet动作读取而来
     * @param map        文件键值对
     * @param type       类型
     */
    protected void generatorCan(List<CanAction> canActions, Map<String, Path> map, TestCaseTypeEnum type) {
        String base = map.get(CAN).toString();
        Path actionsPath = Paths.get(base, ACTIONS + ".py");
        List<CanAction> actionList = canActions.stream()
                .filter(canAction -> canAction.getCategory().equalsIgnoreCase(ACTION))
                .collect(Collectors.toList());
        builder.build(ACTIONS, actionList, TestCaseTypeEnum.COMMON, actionsPath);
        Path conditionsPath = Paths.get(base, CONDITIONS + ".py");
        List<CanAction> conditionList = canActions.stream()
                .filter(canAction -> canAction.getCategory().equalsIgnoreCase(CONDITION))
                .collect(Collectors.toList());
        builder.build(CONDITIONS, conditionList, TestCaseTypeEnum.COMMON, conditionsPath);
        Path directorPath = Paths.get(base, DIRECTOR + ".py");
        generatorCommon(DIRECTOR, directorPath, type);
        Path servicePath = Paths.get(base, SERVICE + ".py");
        builder.setProjectName(configure.getProjectName().toLowerCase());
        builder.build(SERVICE,  type, servicePath);
    }

    protected void generatorTools(Map<String, Path> map, TestCaseTypeEnum type) {
        String base = map.get(TOOLS).toString();
        Path imageProperty = Paths.get(base, IMAGE_PROPERTY + ".py");
        generatorCommon(IMAGE_PROPERTY, imageProperty, null);
        Path screenShot = Paths.get(base, SCREEN_SHOT + ".py");
        generatorCommon(SCREEN_SHOT, screenShot, type);
        Path imageCompare = Paths.get(base, IMAGE_COMPARE + ".py");
        generatorCommon(IMAGE_COMPARE, imageCompare, type);
    }

    protected void checkCategoryFunction(String category, Map<String, Function> functionMap) {
        Function function = functionMap.get(category.toLowerCase());
        if (function == null) {
            String e = "类别[" + category + "]没有定义前置条件，请检查";
            throw new RuntimeException(e);
        }
    }

    protected Map<String, Function> handleFunction(List<Function> functions) {
        Map<String, Function> map = new HashMap<>();
        for (Function function : functions) {
            map.put(function.getCategory().toLowerCase(), function);
        }
        return map;
    }

    /**
     * 获取类别的Set
     *
     * @param clusters 测试用例
     * @return 类别的Set
     */
    private Set<String> getClusterCategories(List<? extends Cluster> clusters) {
        Set<String> set = new HashSet<>();
        clusters.forEach(cluster -> set.add(cluster.getCategory().toLowerCase()));
        return set;
    }

    /**
     * 生成Compare对象
     *
     * @param clusters 测试用例
     * @return 分类好的Compare对象
     */
    private Map<String, Map<String, Compare>> generatorCompare(List<? extends Cluster> clusters) {
        Set<String> categories = getClusterCategories(clusters);
        Map<String, Map<String, Compare>> map = new HashMap<>(10);
        for (String category : categories) {
            Map<String, Compare> compareMap = new HashMap<>(10);
            clusters.stream().filter(cluster -> cluster.getCategory().equalsIgnoreCase(category))
                    .forEach(cluster -> compareMap.put(cluster.getExpectFunctions().get(0).getSecond()
                            .toLowerCase(), cluster.getCompare()));
            map.put(category, compareMap);
        }
        return map;
    }


    protected Function getSuite( List<Function> functions, String category){
        for(Function function: functions){
            if (function.getCategory().equalsIgnoreCase(category)){
                return function;
            }
        }
        String e = "not found suite in functions";
        throw new RuntimeException(e);
    }

    /**
     * 生成测试用例文件
     *
     * @param clusters 测试用例
     * @param map      键值对
     * @param type     类型
     */
    protected void generatorCluster(List<? extends Cluster> clusters, Map<String, Path> map, TestCaseTypeEnum type, List<Function> functions) {
        Map<String, List<? extends TestCase>> clusterMap = getCategoryList(clusters);
        Map<String, Function> functionMap = handleFunction(functions);
        Map<String, Map<String, Compare>> compares = generatorCompare(clusters);
        // 处理测试用例部分
        for (Map.Entry<String, List<? extends TestCase>> entry : clusterMap.entrySet()) {
            // 需要针对compare写入python和json文件，其中json文件写入到当前路径下，而python文件写入到config下
            String category = entry.getKey().toLowerCase();
            Function function = getAndCheckFunction(functionMap, category);
            Function suite = getSuite(functions, category);
            List<? extends TestCase> entities = entry.getValue();
            writeCompare(compares.get(category), category, map);
            builder.setTestCaseName(category);
            builder.setSocPort(configure.getSocPort());
            builder.setProjectName(configure.getProjectName().toLowerCase());
            if (type == TestCaseTypeEnum.CLUSTER) {
                Path testcase = Paths.get(map.get(TEST_CASES).toString(), "test_" + category + ".py");
                builder.build(TESTCASE, entities, type, testcase, function, suite);
            } else if (type == TestCaseTypeEnum.AIR_CONDITION) {
                Path actionTestcase = Paths.get(map.get(TEST_ACTIONS).toString(), "test_" + category + ".py");
                Path compareTestcase = Paths.get(map.get(TEST_COMPARES).toString(), "test_" + category + ".py");
                builder.build(ACTION_TESTCASE, entities, type, actionTestcase, function, suite);
                builder.build(COMPARE_TESTCASE, entities, type, compareTestcase, function, suite);
            }
        }
    }

    protected Function getAndCheckFunction(Map<String, Function> functionMap, String category) {
        checkCategoryFunction(category, functionMap);
        return functionMap.get(category);
    }

}
