package com.chinatsp.automation.impl.reader;

import com.chinatsp.automation.api.reader.BaseReader;
import com.chinatsp.automation.api.reader.IReader;
import com.chinatsp.automation.entity.actions.CanAction;
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
public class CanActionsReader extends BaseReader implements IReader<CanAction> {


    @Override
    public List<CanAction> read(Sheet sheet) {
        log.debug("sheet Name = {}", sheet.getSheetName());
        Row firstRow = sheet.getRow(0);
        Map<String, Integer> titleMap = getTitles(firstRow);
        checkMap(titleMap, CanAction.class, sheet.getSheetName());
        List<CanAction> canActionsList = new ArrayList<>();
        for (Row row : sheet) {
            int rowIndex = row.getRowNum();
            log.debug("row index = {}", rowIndex);
            if (rowIndex != 0){
                CanAction canAction = new CanAction();
                parse(row, canAction, titleMap);
                canActionsList.add(canAction);
            }
        }
        return canActionsList;
    }

    private void parse(Row row, CanAction canAction, Map<String, Integer> map) {
        readerUtils.setBaseEntity(canAction, map, row);
        readerUtils.setFunctionEntity(canAction, map, row);
        readerUtils.setActions(canAction, map, row);
    }
}
