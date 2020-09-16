package com.chinatsp.code.template;

import com.chinatsp.code.beans.ClassAttributes;
import com.chinatsp.code.beans.ClassNames;
import com.chinatsp.code.beans.Configures;
import com.philosophy.base.util.ClazzUtils;
import com.philosophy.base.util.EnumUtils;
import com.philosophy.base.util.ParseUtils;
import com.philosophy.character.util.CharUtils;
import com.philosophy.excel.utils.ExcelUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.chinatsp.code.utils.Constant.BASE_ENTITY;
import static com.chinatsp.code.utils.Constant.LEFT_BRACKETS;
import static com.chinatsp.code.utils.Constant.PACKAGE_NAME;
import static com.chinatsp.code.utils.Constant.RIGHT_BRACKETS;

/**
 * 创建Template文件
 *
 * @author lizhe
 * @date 2020/8/31 11:04
 **/
@Component
@Slf4j
public class Template {

    @Resource
    private EnumUtils enumUtils;
    @Resource
    private ExcelUtils excelUtils;
    @Resource
    private ClassAttributes classAttributes;
    @Resource
    private ClassNames classNames;
    @Resource
    private Configures configures;


    /**
     * 获取对象中某个值的内容
     *
     * @param object 对象
     * @param name   名字
     * @return 内容
     */
    @SneakyThrows
    private String getConfigValue(Object object, String name) {
        log.debug("object name is " + object.getClass().getSimpleName() + " and name is " + name);
        name = CharUtils.lowerCase(name);
        Method method = object.getClass().getMethod("get" + CharUtils.upperCase(name));
        String value = (String) method.invoke(object);
        return new String(value.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }


    /**
     * 根据类名获取表头
     *
     * @param className 类名
     * @return 表头
     */
    @SneakyThrows
    private List<String> getTitles(String className) {
        List<String> titles = new LinkedList<>();
        Class<?> clazz = Class.forName(className);
        if (!Modifier.isAbstract(clazz.getModifiers())) {
            Object object = clazz.newInstance();
            Field[] fields = object.getClass().getSuperclass().getDeclaredFields();
            setTitles(titles, fields);
            fields = object.getClass().getDeclaredFields();
            setTitles(titles, fields);
        }
        return titles;
    }

    private void setTitles(List<String> titles, Field[] fields) {
        for (Field field : fields) {
            String name = field.getName();
            String upperName = CharUtils.upperCase(name);
            log.trace("field name is {}", name);
            String chinese = getConfigValue(classAttributes, name);
            titles.add(upperName + "\n" + chinese);
        }
    }

    /**
     * 获取单元格宽度
     *
     * @param cellValue 单元格内容
     * @return 单元格长度
     */
    private int getWidth(String cellValue) {
        String[] lines = cellValue.split("\n");
        int width = 0;
        for (String line : lines) {
            int length = line.getBytes().length;
            int currentWidth = (int) (length * 1.2d * 256 > 12 * 256 ? length * 1.2d * 256 : 12 * 256);
            if (currentWidth > width) {
                width = currentWidth;
            }
        }
        return width;
    }

    /**
     * 设置每一个Sheet中的内容
     *
     * @param sheet     sheet
     * @param cellStyle 样式
     * @param className 类名
     */
    private void setSheet(Sheet sheet, CellStyle cellStyle, String className) {
        Row row = sheet.createRow(0);
        List<String> titles = getTitles(className);
        Map<Integer, String[]> enums = getEnums(className);
        for (int i = 0; i < titles.size(); i++) {
            Cell cell = row.createCell(i);
            String content = titles.get(i);
            cell.setCellValue(content);
            cell.setCellStyle(cellStyle);
            sheet.setColumnWidth(i, getWidth(content));
        }
        // 生成的测试用例模板数量
        int TEMPLATE_SIZE = 200;
        for (int i = 1; i < TEMPLATE_SIZE + 1; i++) {
            row = sheet.createRow(i);
            // 设置单元格为下拉式的菜单，其中数据的值根据枚举中的value确定
            for (Map.Entry<Integer, String[]> entry : enums.entrySet()) {
                Integer index = entry.getKey();
                String[] values = entry.getValue();
                DataValidationHelper helper = sheet.getDataValidationHelper();
                DataValidationConstraint constraint = helper.createExplicitListConstraint(values);
                CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(i, i, index, index);
                DataValidation dataValidation = helper.createValidation(constraint, cellRangeAddressList);
                sheet.addValidationData(dataValidation);
            }
            // 用于设置序号以及画边框
            for (int j = 0; j < titles.size(); j++) {
                Cell cell = row.createCell(j);
                if (j == 0) {
                    cell.setCellValue(i);
                } else {
                    cell.setCellValue("");
                }
                cell.setCellStyle(cellStyle);
            }
        }
    }

    /**
     * 获取枚举在表头中的序号以及可用的枚举值
     *
     * @param className 类名
     * @return 序号, 枚举值数组
     */
    @SneakyThrows
    private Map<Integer, String[]> getEnums(String className) {
        Map<Integer, String[]> enums = new HashMap<>();
        Class<?> clazz = Class.forName(className);
        Object object = clazz.newInstance();
        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (field.getType().isEnum()) {
                String[] enumValues = ParseUtils.toArray(enumUtils.getEnumValues(field.getType()));
                enums.put(i + 3, enumValues);
            }
        }
        return enums;
    }


    /**
     * 创建automotive的模板文件
     *
     * @param path 文件
     */
    @SneakyThrows
    public void createTemplateExcelFile(Path path) {
        Workbook workbook = excelUtils.openWorkbook(path);
        List<String> classes = ClazzUtils.getClazzName(PACKAGE_NAME, true);
        // 去掉了抽象类
        classes.remove(BASE_ENTITY);
        // 设置单元格内容自动换行、四周边框以及居中显示
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setWrapText(true);
        for (String className : classes) {
            String[] splits = className.split("\\.");
            String sheetName = splits[splits.length - 1];
            log.trace("sheetName = {}", sheetName);
            sheetName = CharUtils.upperCase(sheetName);
            String chinese = getConfigValue(classNames, sheetName);
            log.trace("chinese name is = {}", chinese);
            sheetName = CharUtils.upperCase(chinese) + LEFT_BRACKETS + sheetName + RIGHT_BRACKETS;
            Sheet sheet = workbook.createSheet(sheetName);
            setSheet(sheet, cellStyle, className);
        }
        setConfigureSheet(workbook, cellStyle);
        workbook.write(Files.newOutputStream(path));
        excelUtils.close(workbook);
    }

    /**
     * 创建ConfigureSheet
     *
     * @param workbook  workbook
     * @param cellStyle 单元格样式
     */
    @SneakyThrows
    private void setConfigureSheet(Workbook workbook, CellStyle cellStyle) {
        String configure = "configure";
        String chinese = getConfigValue(classNames, configure);
        String sheetName = CharUtils.upperCase(chinese) + LEFT_BRACKETS + CharUtils.upperCase(configure) + RIGHT_BRACKETS;
        Sheet sheet = workbook.createSheet(sheetName);
        // 创建titleRow
        Row titleRow = sheet.createRow(0);
        String[] titles = new String[]{"Id\n序号", "Name\n名字", "Comment\n描述", "Content\n内容"};
        for (int i = 0; i < titles.length; i++) {
            Cell cell = titleRow.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(titles[i]);
        }
        Field[] fields = configures.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fieldName = field.getName();
            Row row = sheet.createRow(i + 1);
            Cell indexCell = row.createCell(0);
            Cell nameCell = row.createCell(1);
            Cell commentCell = row.createCell(2);
            Cell contentCell = row.createCell(3);
            indexCell.setCellStyle(cellStyle);
            nameCell.setCellStyle(cellStyle);
            commentCell.setCellStyle(cellStyle);
            contentCell.setCellStyle(cellStyle);
            indexCell.setCellValue(i + 1);
            nameCell.setCellValue(fieldName);
            commentCell.setCellValue(getConfigValue(configures, fieldName));
            contentCell.setCellValue("");
        }
        // 宽度在非精确的情况下设置就是width * 256
        sheet.setColumnWidth(1, 25 * 256);
        sheet.setColumnWidth(2, 25 * 256);
        sheet.setColumnWidth(3, 80 * 256);
    }
}
