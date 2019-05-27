package com.philosophy.util;

import com.philosophy.tools.Closee;
import com.philosophy.tools.FilePath;
import com.philosophy.tools.Numeric;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExcelUtils {
    private static Logger logger = LogManager.getLogger(ExcelUtils.class);
    /**
     * 创建workbook文件
     *
     * @param path
     * @return
     */
    private static Workbook createWorkbook(Path path) {
        Workbook workbook = null;
        try {
            if (Files.exists(path)){

                logger.error("file[" + path.getRoot() + "] is exist");
            } else {
                String ext = FilePath.getExtension(path).toUpperCase();
                if (ext.equals("XLS")) {
                    workbook = new HSSFWorkbook();
                } else if (ext.equals("XLSX")) {
                    workbook = new XSSFWorkbook();
                }
                // 默认创建一个sheet
                workbook.createSheet("sheet1");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            return workbook;
        }
    }

    /**
     * 打开新的workbook或者已存在的workboo
     *
     * @param path
     * @return
     */
    public static Workbook openWorkbook(Path path) {
        Workbook wb = null;
        if (!Files.exists(path)) {
            return createWorkbook(path);
        }
        InputStream in = null;
        try {
            in = new FileInputStream(path.toFile());
            String ext = FilePath.getExtension(path).toUpperCase();
            if (ext.equals("XLS")) {
                wb = new HSSFWorkbook(new POIFSFileSystem(in));
            } else if (ext.equals("XLSX")) {
                wb = new XSSFWorkbook(in);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            Closee.close(in);
        }
        return wb;
    }

    /**
     * 关闭Workbook
     *
     * @param wb
     */
    public static void closeWorkbook(Workbook wb) {
        Closee.close(wb);
    }

    /**
     * 把Workbook写入文件，调用这个接口就不用调用CloseWorkbook接口
     *
     * @param wb
     * @param file
     */
    public static void writeWorkbook(Workbook wb, File file) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            wb.write(fos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            Closee.close(fos);
            closeWorkbook(wb);
        }
    }

    public static Cell getCell(Sheet sheet, int x, int y) {
        return sheet.getRow(x - 1).getCell(y - 1);
    }

    public static String getCellValue(Cell cell) {
        String result = null;
        CellType type = cell.getCellType();
        switch (type) {
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    String fmt = "";
                    short format = cell.getCellStyle().getDataFormat();
                    if (format == 14 || format == 31 || format == 57 || format == 58) {
                        fmt = "yyyy-MM-dd";
                    } else if (format == 20 || format == 32) {
                        fmt = "HH:mm";
                    }
                    double value = cell.getNumericCellValue();
                    Date date = DateUtil.getJavaDate(value);
                    result = new SimpleDateFormat(fmt).format(date);
                } else {
                    String str = String.valueOf(cell.getNumericCellValue());
                    String[] num = str.split("\\.");
                    if (Integer.parseInt(num[1]) == 0) {
                        result = num[0];
                    } else {
                        result = str;
                    }
                }
                break;
            default:
                result = cell.getStringCellValue();
                break;
        }
        return result;
    }

    public static String getCellValue(Sheet sheet, int x, int y) {
        return getCellValue(getCell(sheet, x, y));
    }

    public static void setCellValue(Cell cell, String value) {
        if (Numeric.isNumber(value)) {
            if (Numeric.isInteger(value)) {
                cell.setCellValue(Long.parseLong(value));
            } else {
                cell.setCellValue(Double.parseDouble(value));
            }
        } else {
            cell.setCellValue(value);
        }
    }

    public static void setCellValue(Sheet sheet, int x, int y, String value) {
        if (isCellExist(sheet, x, y)) {
            setCellValue(getCell(sheet, x, y), value);
        } else {
            setCellValue(createCell(sheet, x, y), value);
        }
    }

    public static boolean isCellExist(Sheet sheet, int x, int y) {
        Row row = sheet.getRow(x - 1);
        Cell cell = row.getCell(y - 1);
        return (cell == null);
    }

    public static Cell createCell(Sheet sheet, int x, int y) {
        return sheet.createRow(x - 1).createCell(y - 1);
    }

    public static CellStyle getCellStyle(Sheet sheet, int x, int y) {
        return getCell(sheet, x, y).getCellStyle();
    }

    public static void setCellStyle(Sheet sheet, int x, int y, CellStyle style) {
        getCell(sheet, x, y).setCellStyle(style);
    }

    /**
     * 对sheet添加内容
     *
     * @param sheet
     * @param titles
     * @param contents
     */
    public static void writeContent(Sheet sheet, String[] titles, List<String[]> contents) {
        contents.add(0, titles);
        writeContent(sheet, contents);
    }

    /**
     * 对sheet添加内容
     *
     * @param sheet
     * @param contents
     */
    public static void writeContent(Sheet sheet, List<String[]> contents) {
        for (int i = 0; i < contents.size(); i++) {
            String[] content = contents.get(i);
            Row row = sheet.createRow(i);
            for (int j = 0; j < content.length; j++) {
                logger.debug("value is [" + i + ", " + j + "] = " + content[j]);
                Cell cell = row.createCell(j);
                setCellValue(cell, content[j]);
            }
        }
    }
}
