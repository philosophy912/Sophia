package com.philosophy.excel.utils;

import com.philosophy.base.common.Closee;
import com.philosophy.excel.common.ExcelBase;
import com.philosophy.excel.common.ExcelReader;
import com.philosophy.excel.common.ExcelWriter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


/**
 * @author lizhe
 * @date 2019/10/16:14:31
 */
@Slf4j
public class ExcelUtils extends ExcelBase {



    /**
     * 读取sheet中的所有内容
     *
     * @param path      excel文件
     * @param sheetName sheet的名字
     * @return sheet的所有内容
     * @throws IOException IO异常
     */
    public List<String[]> read(Path path, String sheetName) throws IOException {
        Workbook workbook = openWorkbook(path);
        ExcelReader reader = new ExcelReader(workbook);
        reader.readSheet(sheetName);
        List<String[]> content = reader.read();
        reader.close();
        return content;
    }

    /**
     * 读取指定excel文件的指定单元格的值
     *
     * @param path        excel文件
     * @param sheetName   sheet的名字
     * @param rowIndex    行
     * @param columnIndex 列
     * @return 读取到的单元格的值
     * @throws IOException IO异常
     */
    public String read(Path path, String sheetName, int rowIndex, int columnIndex) throws IOException {
        Workbook workbook = openWorkbook(path);
        ExcelReader reader = new ExcelReader(workbook);
        String result = reader.read(sheetName, rowIndex, columnIndex);
        reader.close();
        return result;
    }

    /**
     * 写入内容到文件中
     *
     * @param path      要写入的excel文件
     * @param sheetName 要创建的sheet的名字
     * @param contents  sheet的内容
     */
    public void write(Path path, String sheetName, String[] contents) throws IOException {
        Workbook workbook = openWorkbook(path);
        ExcelWriter writer = new ExcelWriter(workbook, path);
        writer.createSheet(sheetName);
        writer.write(contents);
        writer.close();
    }

    /**
     * 写入内容到文件中
     *
     * @param path      要写入的excel文件
     * @param sheetName 要创建的sheet的名字
     * @param contents  sheet的内容
     */
    public void write(Path path, String sheetName, List<String[]> contents) throws IOException {
        Workbook workbook = openWorkbook(path);
        ExcelWriter writer = new ExcelWriter(workbook, path);
        writer.createSheet(sheetName);
        writer.write(contents);
        writer.close();
    }
}
