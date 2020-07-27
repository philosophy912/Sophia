package com.chinatsp.automation.impl.reader;

import com.chinatsp.automation.api.reader.BaseReader;
import com.chinatsp.automation.api.reader.IReader;
import com.chinatsp.automation.entity.testcase.Cluster;
import com.chinatsp.automation.entity.testcase.Receive;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lizhe
 * @date 2020/5/26 16:42
 **/
@Slf4j
@Component
public class ReceiverReader extends BaseReader implements IReader<Receive> {


    @Override
    public List<Receive> read(Sheet sheet) {
        log.debug("class[{}] sheet Name = {}", this.getClass().getName(), sheet.getSheetName());
        Row firstRow = sheet.getRow(0);
        Map<String, Integer> titleMap = getTitles(firstRow);
        checkMap(titleMap, Receive.class, sheet.getSheetName());
        List<Receive> receives = new ArrayList<>();
        for (Row row : sheet) {
            int rowIndex = row.getRowNum();
            log.debug("row index = {}", rowIndex);
            if (rowIndex != 0) {
                Receive receive = new Receive();
                parse(row, receive, titleMap);
                receives.add(receive);
            }
        }
        return receives;
    }

    private void parse(Row row, Receive receive, Map<String, Integer> map) {
        readerUtils.setBaseEntity(receive, map, row);
        readerUtils.setFunctionEntity(receive, map, row);
        readerUtils.setTestCase(receive, map, row);
        readerUtils.setCluster(receive, map, row);
        readerUtils.setActions(receive, map, row);
    }
}
