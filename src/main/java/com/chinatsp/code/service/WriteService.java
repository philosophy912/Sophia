package com.chinatsp.code.service;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.enumeration.ConfigureTypeEnum;
import com.chinatsp.code.service.api.IWriteService;
import com.chinatsp.code.writer.FreeMarkerWriter;
import com.chinatsp.code.writer.api.TestCaseFreeMarkers;
import com.chinatsp.code.writer.impl.testcase.TestCaseWriter;
import com.chinatsp.dbc.entity.Message;
import com.philosophy.base.common.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static com.chinatsp.code.utils.Constant.TEMPLATE_ACTION;
import static com.chinatsp.code.utils.Constant.TEMPLATE_COMPARE;
import static com.chinatsp.code.utils.Constant.TEMPLATE_TESTCASE;

@Service
public class WriteService implements IWriteService {
    @Resource
    private FreeMarkerWriter freeMarkerWriter;
    @Resource
    private TestCaseWriter testCaseWriter;


    @Override
    public void write(Pair<Map<String, List<BaseEntity>>, Map<ConfigureTypeEnum, String>> pair, List<Message> messages, Path folder) {
        String folderPath = folder.toAbsolutePath().toString();
        Map<ConfigureTypeEnum, String> map = pair.getSecond();
        Map<String, List<BaseEntity>> entities = pair.getFirst();
        Path configure = Paths.get(folderPath, "configure.py");
        freeMarkerWriter.writeConfigure(map, configure);
        Path context = Paths.get(folderPath, "context.py");
        freeMarkerWriter.writeEntity(entities, messages, context);
        Map<String, Pair<TestCaseFreeMarkers, TestCaseFreeMarkers>> freeMarkersMap = testCaseWriter.convert(entities);
        for (Map.Entry<String, Pair<TestCaseFreeMarkers, TestCaseFreeMarkers>> entry : freeMarkersMap.entrySet()) {
            // 文件名小写
            String key = entry.getKey().toLowerCase();
            Pair<TestCaseFreeMarkers, TestCaseFreeMarkers> freeMarkersPair = entry.getValue();
            TestCaseFreeMarkers full = freeMarkersPair.getFirst();
            TestCaseFreeMarkers half = freeMarkersPair.getSecond();
            if (full.getTestcases().size() > 0) {
                // 写入全自动测试用例
                Path testcase = Paths.get(folderPath, "test_" + key + ".py");
                freeMarkerWriter.writeTestCase(full, testcase, TEMPLATE_TESTCASE);
            }
            if (half.getTestcases().size() > 0) {
                // 对于半自动测试用例需要写两份
                Path action = Paths.get(folderPath, "test_" + key + "_action.py");
                freeMarkerWriter.writeTestCase(half, action, TEMPLATE_ACTION);
                Path compare = Paths.get(folderPath, "test_" + key + "_compare.py");
                freeMarkerWriter.writeTestCase(half, compare, TEMPLATE_COMPARE);
            }
        }
    }
}
