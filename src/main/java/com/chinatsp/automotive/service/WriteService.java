package com.chinatsp.automotive.service;

import com.alibaba.fastjson.JSON;
import com.chinatsp.automotive.entity.BaseEntity;
import com.chinatsp.automotive.enumeration.ConfigureTypeEnum;
import com.chinatsp.automotive.service.api.IWriteService;
import com.chinatsp.automotive.writer.FreeMarkerWriter;
import com.chinatsp.automotive.writer.api.TestCaseFreeMarkers;
import com.chinatsp.automotive.writer.impl.testcase.TestCaseWriter;
import com.chinatsp.dbc.entity.Message;
import com.philosophy.base.common.Pair;
import com.philosophy.base.common.Triple;
import com.philosophy.base.util.StringsUtils;
import com.philosophy.txt.util.TxtUtils;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static com.chinatsp.automotive.utils.Constant.CODES;
import static com.chinatsp.automotive.utils.Constant.DBC;
import static com.chinatsp.automotive.utils.Constant.TEMPLATE_ACTION;
import static com.chinatsp.automotive.utils.Constant.TEMPLATE_COMPARE;
import static com.chinatsp.automotive.utils.Constant.TEMPLATE_TESTCASE;
import static com.chinatsp.automotive.utils.Constant.TEST_CASE;

@Service
public class WriteService implements IWriteService {
    @Resource
    private FreeMarkerWriter freeMarkerWriter;
    @Resource
    private TestCaseWriter testCaseWriter;
    @Resource
    private TxtUtils txtUtils;


    @SneakyThrows
    @Override
    public void write(Triple<Map<String, List<BaseEntity>>, Map<ConfigureTypeEnum, String>, List<Message>> triple, Map<String, Path> folders) {
        Map<ConfigureTypeEnum, String> map = triple.getSecond();
        Map<String, List<BaseEntity>> entities = triple.getFirst();
        List<Message> messages = triple.getThird();
        // 写codes，包含configure和context两个文件
        String codesAbsolutePath = folders.get(CODES).toAbsolutePath().toString();
        Path configure = Paths.get(codesAbsolutePath, "configure.py");
        Path context = Paths.get(codesAbsolutePath, "context.py");
        freeMarkerWriter.writeConfigure(map, configure);
        freeMarkerWriter.writeEntity(entities, triple.getThird(), context);
        // 写testcase
        String testcaseAbsolutePath = folders.get(TEST_CASE).toAbsolutePath().toString();
        Map<String, Pair<TestCaseFreeMarkers, TestCaseFreeMarkers>> freeMarkersMap = testCaseWriter.convert(entities);
        for (Map.Entry<String, Pair<TestCaseFreeMarkers, TestCaseFreeMarkers>> entry : freeMarkersMap.entrySet()) {
            // 文件名小写
            String key = entry.getKey().toLowerCase();
            Pair<TestCaseFreeMarkers, TestCaseFreeMarkers> freeMarkersPair = entry.getValue();
            TestCaseFreeMarkers full = freeMarkersPair.getFirst();
            TestCaseFreeMarkers half = freeMarkersPair.getSecond();
            if (full.getTestcases().size() > 0) {
                // 写入全自动测试用例
                Path testcase = Paths.get(testcaseAbsolutePath, "test_" + key + ".py");
                freeMarkerWriter.writeTestCase(full, testcase, TEMPLATE_TESTCASE);
            }
            if (half.getTestcases().size() > 0) {
                // 对于半自动测试用例需要写两份
                Path action = Paths.get(testcaseAbsolutePath, "test_" + key + "_action.py");
                freeMarkerWriter.writeTestCase(half, action, TEMPLATE_ACTION);
                Path compare = Paths.get(testcaseAbsolutePath, "test_" + key + "_compare.py");
                freeMarkerWriter.writeTestCase(half, compare, TEMPLATE_COMPARE);
            }
        }
        // 写DBC
        String dbcAbsolutePath = folders.get(DBC).toAbsolutePath().toString();
        if (messages != null) {
            String content = JSON.toJSONString(messages);
            String dbcFile = map.get(ConfigureTypeEnum.DBC_JSON);
            if (StringsUtils.isEmpty(dbcFile)){
                String error = "需要在Sheet[配置(Configure)]中填写[DBC解析后生成的文件名称]内容";
                throw new RuntimeException(error);
            }
            Path dbc = Paths.get(dbcAbsolutePath, dbcFile);
            txtUtils.write(dbc, content, "utf-8", false, false);
        }

    }
}
