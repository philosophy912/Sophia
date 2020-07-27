package com.chinatsp.automation.impl.reader;

import com.chinatsp.automation.api.reader.BaseReader;
import com.chinatsp.automation.api.reader.IReader;
import com.chinatsp.automation.entity.actions.CanAction;
import com.chinatsp.automation.entity.testcase.Cluster;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lizhe
 * @date 2020/5/26 16:44
 **/
@Component
@Slf4j
public class ClusterReader extends BaseReader implements IReader<Cluster> {


    @Override
    public List<Cluster> read(Sheet sheet) {
        log.debug("sheet Name = {}", sheet.getSheetName());
        Row firstRow = sheet.getRow(0);
        Map<String, Integer> titleMap = getTitles(firstRow);
        checkMap(titleMap, Cluster.class, sheet.getSheetName());
        List<Cluster> clusters = new ArrayList<>();
        for (Row row : sheet) {
            int rowIndex = row.getRowNum();
            log.debug("row index = {}", rowIndex);
            if (rowIndex != 0) {
                Cluster cluster = new Cluster();
                parse(row, cluster, titleMap);
                clusters.add(cluster);
            }
        }
        return clusters;
    }

    private void parse(Row row, Cluster cluster, Map<String, Integer> map) {
        readerUtils.setBaseEntity(cluster, map, row);
        readerUtils.setFunctionEntity(cluster, map, row);
        readerUtils.setTestCase(cluster, map, row);
        readerUtils.setCluster(cluster, map, row);
    }
}
