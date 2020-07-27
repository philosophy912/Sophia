package com.chinatsp.automation.impl.reader;

import com.chinatsp.automation.api.reader.BaseReader;
import com.chinatsp.automation.api.reader.IReader;
import com.chinatsp.automation.entity.actions.ScreenAction;
import com.chinatsp.automation.entity.testcase.Send;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lizhe
 * @date 2020/5/26 16:43
 **/
@Component
@Slf4j
public class SenderReader extends BaseReader implements IReader<Send> {


    @Override
    public List<Send> read(Sheet sheet) {
        log.debug("sheet Name = {}", sheet.getSheetName());
        Row firstRow = sheet.getRow(0);
        Map<String, Integer> titleMap = getTitles(firstRow);
        checkMap(titleMap, Send.class, sheet.getSheetName());
        List<Send> sends = new ArrayList<>();
        for (Row row : sheet) {
            int rowIndex = row.getRowNum();
            log.debug("row index = {}", rowIndex);
            if (rowIndex != 0) {
                Send send = new Send();
                parse(row, send, titleMap);
                sends.add(send);
            }
        }
        return sends;
    }

    private void parse(Row row, Send send, Map<String, Integer> map) {
        readerUtils.setBaseEntity(send, map, row);
        readerUtils.setFunctionEntity(send, map, row);
        readerUtils.setTestCase(send, map, row);
        readerUtils.setAirConditionSend(send, map, row);
    }
}
