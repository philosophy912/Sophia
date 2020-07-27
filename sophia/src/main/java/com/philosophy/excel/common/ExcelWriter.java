package com.philosophy.excel.common;


import com.philosophy.base.common.Closee;
import com.philosophy.base.util.NumericUtils;
import com.philosophy.excel.api.IExcelWriter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * @author lizhe
 * @since V1.0.0 2019/10/20 9:20
 **/
@Slf4j
public class ExcelWriter implements IExcelWriter<String> {

    @Setter
    private Workbook workbook;
    @Setter
    private Path path;
    private OutputStream outputStream;
    private Sheet sheet;

    public ExcelWriter(Workbook workbook, Path path) {
        this.workbook = workbook;
        this.path = path;
    }

    /**
     * 将workbook内容写入文件
     *
     * @throws IOException IO异常
     */
    private void write() throws IOException {
        outputStream = Files.newOutputStream(path);
        workbook.write(outputStream);
    }

    /**
     * 设置单元格的内容
     * 字符串如果识别为数字则以数字方式写入，否则以字符串方式写入
     *
     * @param cell  单元格
     * @param value 值
     */
    public static void setCellValue(Cell cell, String value) {
        if (NumericUtils.isNumber(value)) {
            if (NumericUtils.isInteger(value)) {
                cell.setCellValue(Long.parseLong(value));
            } else {
                cell.setCellValue(Double.parseDouble(value));
            }
        } else {
            cell.setCellValue(value);
        }
    }

    @Override
    public void createSheet(String sheetName) {
        sheet = workbook.createSheet(sheetName);
    }

    @Override
    public void write(String[] contents) throws IOException {
        Row row = sheet.createRow(0);
        int columnIndex = 0;
        for (String content : contents) {
            log.debug("Cell[{},{}] = {}", 0, columnIndex, content);
            Cell cell = row.createCell(columnIndex);
            setCellValue(cell, content);
            columnIndex++;
        }
        write();
    }

    @Override
    public void write(List<String[]> contents) throws IOException {
        int rowIndex = 0;
        for (String[] content : contents) {
            Row row = sheet.createRow(rowIndex);
            int columnIndex = 0;
            for (String cellValue : content) {
                log.debug("Cell[{},{}] = {}", rowIndex, columnIndex, cellValue);
                Cell cell = row.createCell(columnIndex);
                setCellValue(cell, cellValue);
                columnIndex++;
            }
            rowIndex++;
        }
        write();
    }

    @Override
    public void close() {
        Closee.close(workbook, outputStream);
    }


}
