package com.philosophy.excel.common;

import com.philosophy.base.common.Closee;
import com.philosophy.base.util.FilesUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;

/**
 * @author lizhe
 * @since V1.0.0 2019/10/20 10:11
 **/
@Slf4j
public class ExcelBase {
    public static final String XLS = "XLS";
    public static final String XLSX = "XLSX";

    /**
     * 根据sheet名字获取sheet所在位置
     *
     * @param sheetName sheet名字
     * @return 所在位置
     */
    public int getSheetIndex(Workbook workbook, String sheetName) {
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            String sheet = workbook.getSheetAt(i).getSheetName().trim();
            if (sheetName.equals(sheet)) {
                return i;
            }
        }
        throw new RuntimeException("no sheetName [" + sheetName + "] found in workbook");
    }


    /**
     * 创建workbook
     *
     * @param path 文件
     * @throws IOException IO异常
     */
    private Workbook createWorkbook(Path path) throws IOException {
        if (Files.exists(path)) {
            throw new RuntimeException("path[" + path.toString() + "] is exist");
        }
        String ext = FilesUtils.getExtension(path).toUpperCase();
        if (!(XLS.equals(ext) || XLSX.equals(ext))) {
            throw new RuntimeException("file must be xls or xlsx");
        }
        if (XLS.equals(ext)) {
            return new HSSFWorkbook();
        } else {
            return new XSSFWorkbook();
        }
    }

    /**
     * 打开excel文件
     *
     * @param path excel文件
     * @throws IOException IO异常
     */
    public Workbook openWorkbook(Path path) throws IOException {
        if (!Files.exists(path)) {
            return createWorkbook(path);
        } else {
            InputStream inputStream = Files.newInputStream(path);
            String ext = FilesUtils.getExtension(path).toUpperCase();
            if (!(XLS.equals(ext) || XLSX.equals(ext))) {
                throw new RuntimeException("file must be xls or xlsx");
            }
            if (XLS.equals(ext)) {
                return new HSSFWorkbook(new POIFSFileSystem(inputStream));
            } else {
                return new XSSFWorkbook(inputStream);
            }
        }
    }

    /**
     * 指定sheet中的指定行列是否存在
     *
     * @param workbook    workbook
     * @param sheetName   sheet名字
     * @param rowIndex    自然行
     * @param columnIndex 自然列
     * @return 存在返回true，不存在返回false
     */
    public boolean isCellExist(Workbook workbook, String sheetName, int rowIndex, int columnIndex) {
        try {
            int sheetIndex = getSheetIndex(workbook, sheetName);
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            Cell cell = sheet.getRow(rowIndex - 1).getCell(columnIndex - 1);
            return cell != null;
        } catch (Exception e) {
            log.debug("cell not found in sheet {} and cell[{},{}]", sheetName, rowIndex, columnIndex);
            return false;
        }
    }

    /**
     * 获取单元格的值
     *
     * @param cell 单元格
     * @return 单元格的值
     */
    public String getCellValue(Cell cell) {
        String result;
        CellType type = cell.getCellType();
        switch (type) {
            case BOOLEAN:
                result = String.valueOf(cell.getBooleanCellValue());
                break;
            case NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    result = sdf.format(cell.getDateCellValue());
                } else {
                    String str = String.valueOf(cell.getNumericCellValue());
                    log.debug("str = {}", str);
                    String science = "E";
                    if (science.contains(str)) {
                        return new BigDecimal(str).toPlainString();
                    } else {
                        String[] num = str.split("\\.");
                        if (num.length == 1) {
                            return str;
                        } else {
                            try {
                                int value = Integer.parseInt(num[1]);
                                if (value == 0) {
                                    return num[0];
                                } else {
                                    return str;
                                }
                            } catch (NumberFormatException e) {
                                log.debug(e.getMessage());
                                return str;
                            }
                        }
                    }

                }
                break;
            default:
                result = cell.getStringCellValue();
                break;
        }
        return result;
    }

    /**
     * 处理sheet
     *
     * @param wb               读取出来的workbook
     * @param sheetName        sheet的名字
     * @param isRemoveFirstRow 是否去除第一行
     * @return sheet对象
     */
    private Sheet handleSheet(Workbook wb, String sheetName, boolean isRemoveFirstRow) {
        int sheetIndex = getSheetIndex(wb, sheetName);
        Sheet sheet = wb.getSheetAt(sheetIndex);
        if (isRemoveFirstRow) {
            // 去掉第一行的内容
            sheet.removeRow(sheet.getRow(0));
        }
        log.debug("total excel row is {}", sheet.getPhysicalNumberOfRows());
        return sheet;
    }

    @SneakyThrows
    public Workbook open(Path path) {
        log.info("excel path is [{}]", path.toAbsolutePath());
        if (!Files.exists(path)) {
            throw new RuntimeException("excel file [{" + path + "}] not found in ");
        }
        return openWorkbook(path);
    }

    public Sheet getSheet(Workbook workbook, String sheetName, boolean isRemoveFirstRow) {
        return handleSheet(workbook, sheetName, isRemoveFirstRow);
    }


    public void close(Workbook workbook) {
        Closee.close(workbook);
    }
}
