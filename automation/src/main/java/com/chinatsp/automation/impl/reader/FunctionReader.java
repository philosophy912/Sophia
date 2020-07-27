package com.chinatsp.automation.impl.reader;

import com.chinatsp.automation.api.reader.BaseReader;
import com.chinatsp.automation.api.reader.IReader;
import com.chinatsp.automation.entity.compare.Function;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lizhe
 * @date 2020/6/11 11:06
 **/
@Component
@Slf4j
public class FunctionReader extends BaseReader implements IReader<Function> {
    @Override
    public List<Function> read(Sheet sheet) {
        log.debug("sheet Name = {}", sheet.getSheetName());
        Row firstRow = sheet.getRow(0);
        Map<String, Integer> titleMap = getTitles(firstRow);
        checkMap(titleMap, Function.class, sheet.getSheetName());
        List<Function> functions = new ArrayList<>();
        for (Row row : sheet) {
            int rowIndex = row.getRowNum();
            log.debug("row index = {}", rowIndex);
            if (rowIndex != 0) {
                Function function = new Function();
                parse(row, function, titleMap);
                functions.add(function);
            }
        }
        return functions;
    }

    private void parse(Row row, Function function, Map<String, Integer> map) {
        readerUtils.setBaseEntity(function, map, row);
        readerUtils.setFunctions(function, map, row);
    }
}
