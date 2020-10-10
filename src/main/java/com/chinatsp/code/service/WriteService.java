package com.chinatsp.code.service;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.enumeration.ConfigureTypeEnum;
import com.chinatsp.code.service.api.IWriteService;
import com.chinatsp.code.writer.FreeMarkerWriter;
import com.chinatsp.code.writer.api.TestCaseFreeMarkers;
import com.chinatsp.code.writer.impl.testcase.TestCaseWriter;
import com.philosophy.base.common.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Service
public class WriteService implements IWriteService {
    @Resource
    private FreeMarkerWriter freeMarkerWriter;
    @Resource
    private TestCaseWriter testCaseWriter;


    @Override
    public void write(Pair<Map<String, List<BaseEntity>>, Map<ConfigureTypeEnum, String>> pair, Path folder) {
        String folderPath = folder.toAbsolutePath().toString();
        Map<ConfigureTypeEnum, String> map = pair.getSecond();
        Map<String, List<BaseEntity>> entities = pair.getFirst();
        Path configure = Paths.get(folderPath, "configure.py");
        freeMarkerWriter.writeConfigure(map, configure);
        Path context = Paths.get(folderPath, "context.py");
        freeMarkerWriter.writeEntity(entities, context);
        Map<String, Pair<TestCaseFreeMarkers, TestCaseFreeMarkers>> freeMarkersMap = testCaseWriter.convert(entities);
        for (Map.Entry<String, Pair<TestCaseFreeMarkers, TestCaseFreeMarkers>> entry : freeMarkersMap.entrySet()) {
            // 文件名小写
            String key = entry.getKey().toLowerCase();
            Pair<TestCaseFreeMarkers, TestCaseFreeMarkers> freeMarkersPair = entry.getValue();
            Path action = Paths.get(folderPath, "test_" + key + ".py");
            freeMarkerWriter.writeTestCase(freeMarkersPair.getFirst(), action);
            // todo 写入的话需要两次，一次写入action，一次写入compare
            Path compare = Paths.get(folderPath, "test_" + key + "_compare.py");
            freeMarkerWriter.writeTestCase(freeMarkersPair.getSecond(), compare);
        }
    }
}
