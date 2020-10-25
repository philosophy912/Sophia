package com.chinatsp.code.runner;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.enumeration.ConfigureTypeEnum;
import com.chinatsp.code.service.ReaderService;
import com.chinatsp.code.service.TemplateService;
import com.chinatsp.code.service.WriteService;
import com.chinatsp.dbc.entity.Message;
import com.philosophy.base.common.Triple;
import com.philosophy.base.util.FilesUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.chinatsp.code.utils.Constant.CODES;
import static com.chinatsp.code.utils.Constant.DBC;
import static com.chinatsp.code.utils.Constant.TEST_CASE;
import static com.chinatsp.code.utils.Constant.TOP;

/**
 * @author lizhe
 * @date 2020/10/15 16:55
 **/
@Slf4j
@Component
public class CodeRunner implements CommandLineRunner {
    @Resource
    private ReaderService readerService;
    @Resource
    private WriteService writeService;
    @Resource
    private TemplateService templateService;

    private static final String FILE_FOLDER = "file";
    private static final String TEST_CASE_FILE_NAME_XLS = "testcase.xls";
    private static final String TEST_CASE_FILE_NAME_XLSX = "testcase.xlsx";

    private Path getSub(Path path, String... names) {
        return Paths.get(path.toAbsolutePath().toString(), names);
    }

    private void create(Path path, Path initFile) throws IOException {
        String absolutePath = path.toAbsolutePath().toString();
        log.debug("Path is {}", absolutePath);
        Files.createDirectories(path);
        Path targetPath = Paths.get(absolutePath, "__init__.py");
        Files.copy(initFile, targetPath, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * 创建path
     * 目录结构
     * 时间戳作为文件夹名
     * src
     * --codes
     * --resources
     * ----dbc
     * ----templates
     * --result
     * ----report
     * ----screenshot
     * ----temp
     * --testcase
     *
     * @param path 文件夹
     */
    @SneakyThrows
    private Map<String, Path> createFolders(Path path) {
        Map<String, Path> folders = new HashMap<>();
        Path initFile = getSub(path, "templates", "__init__.py");
        // 获取当前时间作为时间戳
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path top = Paths.get(dateTime);
        Files.createDirectories(top);
        Path src = getSub(top, "src");
        // 创建src文件夹并拷贝init文件
        create(src, initFile);
        // 创建src文件夹并拷贝init文件
        Path codes = getSub(src, "codes");
        create(codes, initFile);
        // 创建resources文件夹并拷贝init文件
        Path resources = getSub(src, "resources");
        create(resources, initFile);
        // 创建dbc文件夹并拷贝init文件
        Path dbc = getSub(resources, "dbc");
        create(dbc, initFile);
        // 创建templates文件夹并拷贝init文件
        Path templates = getSub(resources, "templates");
        create(templates, initFile);
        // 创建resources文件夹并拷贝init文件
        Path result = getSub(src, "result");
        create(result, initFile);
        // 创建report文件夹并拷贝init文件
        Path report = getSub(result, "report");
        create(report, initFile);
        // 创建screenshot文件夹并拷贝init文件
        Path screenshot = getSub(result, "report");
        create(screenshot, initFile);
        // 创建temp文件夹并拷贝init文件
        Path temp = getSub(result, "report");
        create(temp, initFile);
        // 创建testcase文件夹并拷贝init文件
        Path testcase = getSub(src, "testcase");
        create(testcase, initFile);
        folders.put(TOP, top);
        folders.put(CODES, codes);
        folders.put(DBC, dbc);
        folders.put(TEST_CASE, testcase);
        return folders;
    }


    @SneakyThrows
    @Override
    public void run(String... args) {
        /*
         * 1、当前未传入参数的时候，且在file文件夹下未找到excel文件，则只在当前文件夹下面生成template.xls文件
         * 2、当前未传入参数，且在file文件夹下面找到了testcase.xls或者testcase.xlsx文件，则生成代码
         * 3、当前传入了参数且为excel文件，则生成代码
         * 4、当前传入了参数但不为excel文件，则只生成template文件
         */
        // 获取程序执行当前的文件夹路径
        Path runTimeFolder = Paths.get(FilesUtils.getCurrentPath());
        if (args.length == 1) {
            String param = args[0];
            if (param.endsWith("xls") || param.endsWith("xlsx")) {
                // 当前文件夹下面的文件名
                Path testcase = Paths.get(runTimeFolder.toAbsolutePath().toString(), param);
                if (!Files.exists(testcase)) {
                    throw new RuntimeException("当前文件夹下找不到[" + param + "]文件");
                }
                Map<String, Path> folders = createFolders(runTimeFolder);
                log.info("开始生成[{}]描述的测试用例代码", param);
                generator(testcase, folders);
            }else{
                log.info("生成模板文件template.xls");
                templateService.createTemplate(runTimeFolder);
            }
        } else {
            Path base = Paths.get(FilesUtils.getCurrentPath(), FILE_FOLDER);
            Path testCaseXls = Paths.get(base.toAbsolutePath().toString(),TEST_CASE_FILE_NAME_XLS);
            Path testCaseXlsx = Paths.get(base.toAbsolutePath().toString(),TEST_CASE_FILE_NAME_XLSX);
            if(Files.exists(testCaseXls)){
                Map<String, Path> folders = createFolders(runTimeFolder);
                log.info("开始生成[{}]描述的测试用例代码", TEST_CASE_FILE_NAME_XLS);
                generator(testCaseXls, folders);
            }else if (Files.exists(testCaseXlsx)){
                Map<String, Path> folders = createFolders(runTimeFolder);
                log.info("开始生成[{}]描述的测试用例代码", TEST_CASE_FILE_NAME_XLSX);
                generator(testCaseXlsx, folders);
            }else{
                log.info("生成模板文件template.xls");
                templateService.createTemplate(runTimeFolder);
            }
        }
    }

    private void generator(Path testcaseXlsx, Map<String, Path> folders) {
        Triple<Map<String, List<BaseEntity>>, Map<ConfigureTypeEnum, String>, List<Message>> triple = readerService.read(testcaseXlsx);
        writeService.write(triple, folders);
    }
}
