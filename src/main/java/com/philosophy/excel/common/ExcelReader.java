package com.philosophy.excel.common;

import com.philosophy.base.common.Closee;
import com.philosophy.excel.api.IExcelReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhe
 * @since V1.0.0 2019/10/20 9:27
 **/
@Slf4j
public class ExcelReader extends ExcelBase implements IExcelReader<String[]> {

    private Workbook workbook;
    private Sheet sheet;

    public ExcelReader(Workbook workbook) {
        this.workbook = workbook;
    }


    @Override
    public void readSheet(String sheetName) {
        int sheetIndex = getSheetIndex(workbook, sheetName);
        sheet = workbook.getSheetAt(sheetIndex);
    }

    @Override
    public String read(String sheetName, int rowIndex, int columnIndex) {
        readSheet(sheetName);
        if (!isCellExist(workbook, sheetName, rowIndex, columnIndex)) {
            throw new RuntimeException("cell [" + rowIndex + ", " + columnIndex + "] " +
                    "is not found in sheet [" + sheetName + "]");
        }
        Cell cell = sheet.getRow(rowIndex - 1).getCell(columnIndex - 1);
        return getCellValue(cell);
    }

    @Override
    public List<String[]> read() {
        List<String[]> contents = new ArrayList<>();
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            int columnCount = row.getPhysicalNumberOfCells();
            String[] content = new String[columnCount];
            for (int j = 0; j < columnCount; j++) {
                Cell cell = row.getCell(j);
                String cellValue = getCellValue(cell);
                content[j] = cellValue;
                log.debug("cell[{},{}] = {}", i, j, cellValue);
            }
            contents.add(content);
        }
        return contents;
    }

    @Override
    public void close() {
        Closee.close(workbook);
    }
}
