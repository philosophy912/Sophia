package com.chinatsp.tools.service.api;

import com.alibaba.fastjson.JSON;
import com.chinatsp.automation.api.builder.TestCaseTypeEnum;
import com.chinatsp.automation.entity.base.TestCase;
import com.chinatsp.automation.entity.compare.Compare;
import com.chinatsp.automation.impl.builder.CodeBuilder;
import com.chinatsp.automation.impl.checker.CanActionsCheck;
import com.chinatsp.automation.impl.checker.ClusterCheck;
import com.chinatsp.automation.impl.checker.FunctionCheck;
import com.chinatsp.automation.impl.checker.ReceiverCheck;
import com.chinatsp.automation.impl.checker.ScreenActionsCheck;
import com.chinatsp.automation.impl.checker.SenderCheck;
import com.chinatsp.automation.impl.reader.CanActionsReader;
import com.chinatsp.automation.impl.reader.ClusterReader;
import com.chinatsp.automation.impl.reader.FunctionReader;
import com.chinatsp.automation.impl.reader.ReceiverReader;
import com.chinatsp.automation.impl.reader.ScreenActionsReader;
import com.chinatsp.automation.impl.reader.SenderReader;
import com.chinatsp.automation.impl.utils.ReaderUtils;
import com.chinatsp.dbc.entity.Message;
import com.chinatsp.dbc.impl.DbcParser;
import com.chinatsp.dbc.impl.ExcelDbcParser;
import com.chinatsp.tools.config.ProjectConfig;
import com.chinatsp.tools.utils.ToolUtils;
import com.philosophy.base.util.FilesUtils;
import com.philosophy.excel.utils.ExcelUtils;
import com.philosophy.txt.util.TxtUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.chinatsp.automation.api.IConstant.DBC;
import static com.chinatsp.dbc.api.IConstant.UTF8;
import static com.chinatsp.tools.config.IConstant.COMMON;
import static com.chinatsp.tools.config.IConstant.CONFIG;
import static com.chinatsp.tools.config.IConstant.FILES;
import static com.chinatsp.tools.config.IConstant.JSONS;
import static com.chinatsp.tools.config.IConstant.PORT;
import static com.chinatsp.tools.config.IConstant.SRC;
import static com.chinatsp.tools.config.IConstant.TESTCASE;
import static com.chinatsp.tools.config.IConstant.TOP;
import static com.chinatsp.tools.service.api.IService.INSTALL;
import static com.chinatsp.tools.service.api.IService.RUN;
import static com.chinatsp.tools.service.api.IService.SETUP;
import static com.philosophy.excel.common.ExcelBase.XLS;
import static com.philosophy.excel.common.ExcelBase.XLSX;
import static com.chinatsp.tools.service.api.IService.LOGGER;

/**
 * @author lizhe
 * @date 2020/6/4 9:08
 **/
@Slf4j
public abstract class BaseService {

    @Autowired
    protected ProjectConfig configure;
    @Autowired
    protected ExcelUtils excelUtils;
    @Autowired
    protected TxtUtils txtUtils;
    @Autowired
    protected ReaderUtils readerUtils;
    @Autowired
    private DbcParser dbcParser;
    @Autowired
    private ExcelDbcParser excelDbcParser;
    @Autowired
    protected CodeBuilder builder;
    @Autowired
    protected ToolUtils toolUtils;
    @Autowired
    protected CanActionsReader canActionsReader;
    @Autowired
    protected CanActionsCheck canActionsCheck;
    @Autowired
    protected ReceiverReader receiverReader;
    @Autowired
    protected SenderReader senderReader;
    @Autowired
    protected ScreenActionsReader screenActionsReader;
    @Autowired
    protected ReceiverCheck receiverCheck;
    @Autowired
    protected SenderCheck senderCheck;
    @Autowired
    protected ScreenActionsCheck screenActionsCheck;
    @Autowired
    protected ClusterReader clusterReader;
    @Autowired
    protected ClusterCheck clusterCheck;
    @Autowired
    protected FunctionReader functionReader;
    @Autowired
    protected FunctionCheck functionCheck;
    /**
     * 程序运行的当前路径
     */
    protected static final String CURRENT_PATH = FilesUtils.getCurrentPath();

    /**
     * 获取分类类别
     *
     * @param entities 测试用例列表
     * @return 分类的set
     */
    private Set<String> splitCategory(List<? extends TestCase> entities) {
        Set<String> categories = new HashSet<>();
        entities.forEach(entity -> categories.add(entity.getCategory()));
        return categories;
    }

    /**
     * 获取测试用例的分类列表
     *
     * @param entities 测试用例列表
     * @return 每一个分类下面的测试用例列表
     */
    protected Map<String, List<? extends TestCase>> getCategoryList(List<? extends TestCase> entities) {
        Set<String> categories = splitCategory(entities);
        Map<String, List<? extends TestCase>> map = new HashMap<>(10);
        categories.forEach(category -> map.put(category, entities.stream().
                filter(entity -> entity.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList())));
        return map;
    }


    /**
     * 检查并设置dbc以及testcase的路径
     * 该文件必须放到当前运行目录下的files目录下
     */
    protected Map<String, Path> initInputFiles() {
        String dbcName = configure.getDbcName();
        String testcaseName = configure.getTestCaseName();
        log.debug("current path is {} and dbc name is {} and test case name is {}", CURRENT_PATH, dbcName, testcaseName);
        Path dbc = Paths.get(CURRENT_PATH, FILES, dbcName);
        Path testcase = Paths.get(CURRENT_PATH, FILES, testcaseName);
        // 找不到文件中的某一个的时候都会报错
        if (!Files.exists(dbc)) {
            String e = "could not found dbc file[{" + dbc + "}] in current path[{" + CURRENT_PATH + "}]";
            throw new RuntimeException(e);
        }
        if (!Files.exists(testcase)) {
            String e = "could not found testcase file[{" + testcase + "}] in current path[{" + CURRENT_PATH + "}]";
            throw new RuntimeException(e);
        }
        Map<String, Path> map = new HashMap<>(10);
        map.put(DBC, dbc);
        map.put(TESTCASE, testcase);
        return map;
    }

    /**
     * 读取DBC转换成Message对象
     *
     * @param dbcPath dbc文件
     * @return Message对象列表
     */
    @SneakyThrows
    protected List<Message> getMessage(Path dbcPath) {
        if (!Files.exists(dbcPath)) {
            String e = dbcPath.toString() + "不存在，请仔细检查";
            throw new RuntimeException(e);
        }
        String extension = FilesUtils.getExtension(dbcPath);
        if (extension.equalsIgnoreCase(XLS) || extension.equalsIgnoreCase(XLSX)) {
            // excel方式
            return excelDbcParser.parse(dbcPath);
        } else if (extension.equalsIgnoreCase(DBC)) {
            // dbc方式
            return dbcParser.parse(dbcPath);
        } else {
            String e = "目前只支持[excel]和[dbc]两个模式,当前文件扩展名为[" + extension + "], 不支持";
            throw new RuntimeException(e);
        }
    }

    /**
     * 转换字符串为python代码可用的，主要用于替换true和false
     *
     * @param str    字符串
     * @param prefix 前缀，主要是用于messages = 或者 compare =
     * @return python代码可识别的字符串
     */
    protected String convertPython(String str, String prefix) {
        return prefix + str.replaceAll("true", "True")
                .replaceAll("false", "False");
    }

    /**
     * 把图片对比的文件写入到文件中
     *
     * @param compareMap 分类的Compare对象键值对
     * @param category   类型 用于决定文件名
     * @param map        文件地址，主要是读取JSONS和CONFIG的绝对路径
     */
    @SneakyThrows
    protected void writeCompare(Map<String, Compare> compareMap, String category, Map<String, Path> map) {
        String jsonStr = JSON.toJSONString(compareMap);
        String pythonStr = convertPython(jsonStr, "compare = ");
        Path jsonCompare = Paths.get(map.get(JSONS).toString(), category + ".json");
        Path pythonCompare = Paths.get(map.get(CONFIG).toString(), category + ".py");
        txtUtils.write(jsonCompare, jsonStr, UTF8, false, false);
        txtUtils.write(pythonCompare, pythonStr, UTF8, false, false);
    }

    /**
     * 根据测试用例类型生成文件，
     * 如果是null表示公共模板，则生成templates下面的类型文件
     * 否则生成aircondition或者cluster下面的模板
     *
     * @param templateName 模板文件名
     * @param path         生成的文件绝对路径
     * @param type         生成类型
     */
    @SneakyThrows
    protected void generatorCommon(String templateName, Path path, TestCaseTypeEnum type) {
        if (type == null) {
            type = TestCaseTypeEnum.COMMON;
        }
        FilesUtils.deleteFiles(path);
        builder.setProjectName(configure.getProjectName().toLowerCase());
        builder.build(templateName, type, path);
    }

    /**
     * 判定字符串是否是double类型
     *
     * @param num 数字字符串
     * @return true/false
     */
    private boolean isDouble(String num) {
        try {
            Double.parseDouble(num);
        } catch (NumberFormatException e) {
            log.debug("num is not type double");
            return true;
        }
        return false;
    }

    /**
     * 创建setup相关的文件，主要用于创建下列文件
     * setup.py
     * source/xxx/logger.py
     * install.bat
     * src/run.bat
     * common/logger.py
     *
     * @param map 生成的文件名和具体的绝对路径的键值对
     */
    protected void generatorInstall(Map<String, Path> map) {
        String projectName = configure.getProjectName();
        builder.setProjectName(projectName.toLowerCase());
        String automotive = configure.getAutomotive().trim();
        if (isDouble(automotive)) {
            String e = "automotive must be version number, but now is " + automotive;
            throw new RuntimeException(e);
        }
        builder.setAutomotive(configure.getAutomotive());
        builder.setVersion("1.0");
        Path setupPath = Paths.get(map.get(TOP + projectName.toLowerCase()).toString(), SETUP + ".py");
        Path loggerPath = Paths.get(map.get(projectName.toUpperCase()).toString(), LOGGER + ".py");
        Path srcLoggerPath = Paths.get(map.get(COMMON).toString(), LOGGER + ".py");
        builder.build(SETUP, TestCaseTypeEnum.COMMON, setupPath);
        builder.build(LOGGER, TestCaseTypeEnum.COMMON, loggerPath);
        builder.build(LOGGER, TestCaseTypeEnum.COMMON, srcLoggerPath);
        Path installPath = Paths.get(map.get(TOP + projectName.toLowerCase()).toString(), INSTALL + ".bat");
        builder.build(INSTALL, TestCaseTypeEnum.COMMON, installPath);
        Path runPath = Paths.get(map.get(SRC).toString(), RUN + ".bat");
        builder.build(RUN, TestCaseTypeEnum.COMMON, runPath);
        String port = configure.getSocPort();
        if (port != null) {
            Path portPath = Paths.get(map.get(COMMON).toString(), "port.py");
            builder.setSocPort(port);
            builder.build(PORT, TestCaseTypeEnum.COMMON, portPath);
        }
    }

}
