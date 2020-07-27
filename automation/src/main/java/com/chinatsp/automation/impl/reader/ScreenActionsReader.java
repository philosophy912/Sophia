package com.chinatsp.automation.impl.reader;

import com.chinatsp.automation.api.reader.BaseReader;
import com.chinatsp.automation.api.reader.IReader;
import com.chinatsp.automation.entity.actions.ScreenAction;
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
 * @date 2020/5/26 16:44
 **/
@Slf4j
@Component
public class ScreenActionsReader extends BaseReader implements IReader<ScreenAction> {

    @Override
    public List<ScreenAction> read(Sheet sheet) {
        log.debug("sheet Name = {}", sheet.getSheetName());
        Row firstRow = sheet.getRow(0);
        Map<String, Integer> titleMap = getTitles(firstRow);
        checkMap(titleMap, ScreenAction.class, sheet.getSheetName());
        List<ScreenAction> screenActions = new ArrayList<>();
        for (Row row : sheet) {
            int rowIndex = row.getRowNum();
            log.debug("row index = {}", rowIndex);
            if (rowIndex != 0) {
                ScreenAction screenAction = new ScreenAction();
                parse(row, screenAction, titleMap);
                screenActions.add(screenAction);
            }
        }
        return screenActions;
    }

    private void parse(Row row, ScreenAction screenAction, Map<String, Integer> map) {
        readerUtils.setBaseEntity(screenAction, map, row);
        readerUtils.setFunctionEntity(screenAction, map, row);
        readerUtils.setScreenActions(screenAction, map, row);
    }
}
