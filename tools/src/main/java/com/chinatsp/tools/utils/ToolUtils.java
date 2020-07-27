package com.chinatsp.tools.utils;

import com.chinatsp.automation.api.builder.TestCaseTypeEnum;
import com.chinatsp.tools.config.IConstant;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lizhe
 * @date 2020-06-03 21:05
 */
@Slf4j
@Component
public class ToolUtils implements IConstant {

    /**
     * 删除除了TEMPLATE和BMP之外的文件
     *
     * @param folder 文件夹
     */
    @SneakyThrows
    public void deleteFolder(String folder) {
        Path dir = Paths.get(folder);
        if (Files.exists(dir)) {
            Files.walk(Paths.get(folder))
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(file -> {
                        if (!(file.toString().endsWith(TEMPLATE) || file.toString().endsWith(BMP))) {
                            boolean result = file.delete();
                            log.debug("delete file {} result is {}", file.toString(), result);
                        }
                    });
        }
    }

    /**
     * 创建文件夹
     *
     * @param parent  父文件夹
     * @param current 当前路径
     * @return 创建的文件夹路径
     */
    public Path createFolder(Path parent, String current) {
        return createFolder(parent.toString(), current);
    }

    /**
     * 创建文件夹
     *
     * @param parent  父文件夹
     * @param current 当前路径
     * @return 创建的文件夹路径
     */
    public Path createFolder(String parent, String current) {
        Path path = Paths.get(parent.toLowerCase(), current);
        checkPath(path);
        return path;
    }

    /**
     * 检查文件夹是否存在，不存在则创建文件夹
     * @param folders 文件夹列表
     */
    @SneakyThrows
    private void checkPath(Path... folders) {
        for (Path folder : folders) {
            if (!Files.exists(folder)) {
                Files.createDirectories(folder);
            }
        }
    }

    /**
     * 创建文件夹
     * --top 顶层文件夹
     * ----projectName 项目lib文件夹
     * ------can 存放所有操作相关的文件
     * ------config 存放DBC解析后的文件以及用于图片对比的配置文件
     * ------tools 存放图片对比等工具的文件
     * ----src 测试用例文件
     * ------actions 操作相关的测试用例
     * ------compares 图片对比相关的测试用例
     * ------auto 纯自动化测试用例
     * ------result 测试结果
     * --------screenshot 截图路径
     * --------template 原始图片路径
     * --------report 报告路径
     * --------temp 临时路径
     * ----jsons 配置文件json版本
     *
     * @param currentPath 当前路径
     * @param projectName 项目名称
     * @return 文件夹及对应的路径
     */
    public Map<String, Path> createDirectories(String currentPath, String projectName, TestCaseTypeEnum type) {
        Map<String, Path> map = new HashMap<>(8);
        Path top = createFolder(currentPath, TOP + projectName);
        // 顶层文件夹
        map.put(TOP + projectName, top);
        Path source = createFolder(top, SOURCE);
        map.put(SOURCE, source);
        // 项目lib文件夹
        Path project = createFolder(source, projectName);
        map.put(projectName.toUpperCase(), project);
        createInitFile(project);
        map.put(CAN, createFolder(project, CAN));
        map.put(CONFIG, createFolder(project, CONFIG));
        map.put(TOOLS, createFolder(project, TOOLS));
        createInitFile(map.get(CAN));
        createInitFile(map.get(CONFIG));
        createInitFile(map.get(TOOLS));
        // 测试用例文件
        Path src = createFolder(top, SRC.toLowerCase());
        map.put(SRC, src);
        map.put(COMMON, createFolder(src, COMMON));
        map.put(TEST_CASES, createFolder(src, TEST_CASES));
        if (type == TestCaseTypeEnum.AIR_CONDITION) {
            map.put(TEST_ACTIONS, createFolder(src, TEST_ACTIONS));
            map.put(TEST_COMPARES, createFolder(src, TEST_COMPARES));
        }
        Path result = createFolder(src, RESULT.toLowerCase());
        map.put(SCREENSHOT, createFolder(result, SCREENSHOT));
        map.put(TEMPLATE, createFolder(result, TEMPLATE));
        map.put(REPORT, createFolder(result, REPORT));
        map.put(TEMP, createFolder(result, TEMP));
        // 配置文件json版本
        map.put(JSONS, createFolder(top, JSONS));
        return map;
    }

    /**
     * 在path文件夹下创建python的init文件
     * @param path 文件夹路径
     */
    @SneakyThrows
    public void createInitFile(Path path) {
        Path initPath = Paths.get(path.toString(), "__init__.py");
        Files.createFile(initPath);
    }

}
