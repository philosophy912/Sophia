package com.chinatsp.code.reader;

import com.chinatsp.code.beans.ClassNames;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.collection.Element;
import com.philosophy.base.common.Reflect;
import com.philosophy.base.util.ClazzUtils;
import com.philosophy.character.util.CharUtils;
import com.philosophy.excel.utils.ExcelUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.chinatsp.code.utils.Constant.PACKAGE_NAME;

/**
 * @author lizhe
 * @date 2020/8/28 11:00
 **/
@Component
@Slf4j
public class Reader {

    private ExcelUtils excelUtils;
    private ClassNames classNames;

    @Autowired
    public void setExcelUtils(ExcelUtils excelUtils) {
        this.excelUtils = excelUtils;
    }

    @Autowired
    public void setClassNames(ClassNames classNames) {
        this.classNames = classNames;
    }

    /**
     * 从excel中读取各个Sheet
     *
     * @param path 测试用例文件
     * @return Map
     */
    @SneakyThrows
    private Map<String, Sheet> readExcel(Path path) {
        Map<String, Sheet> map = new HashMap<>(12);
        Workbook workbook = excelUtils.openWorkbook(path);
        Field[] fields = classNames.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            log.trace("field name is {}", fieldName);
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                String sheetName = sheet.getSheetName();
                log.trace("sheet name is {}", sheetName);
                if (sheetName.toLowerCase().contains(fieldName.toLowerCase())) {
                    map.put(fieldName, sheet);
                }
            }
        }
        excelUtils.close(workbook);
        return map;
    }

    /**
     * 根据sheet名字获取全包名
     *
     * @param sheetName sheet的名字
     * @return sheet对应的实体类的全包名
     */
    private String getFullClassName(String sheetName) {
        List<String> classes = ClazzUtils.getClazzName(PACKAGE_NAME, true);
        for (String className : classes) {
            if (className.toLowerCase().contains(sheetName.toLowerCase())) {
                return className;
            }
        }
        throw new RuntimeException("can not found sheetName in " + PACKAGE_NAME);
    }

    /**
     * 把title行转换成为对应的列号和名字
     * 如: Common对象则转换成
     * 1 -> id
     * 2 -> name
     * 3 -> comment
     * 4 -> params
     *
     * @param titleRow 表头
     * @param clazz    转换的对象
     * @return 对应的字典
     */
    private Map<String, Integer> getEntityAttributeMap(Row titleRow, Class<?> clazz) {
        Map<String, Integer> map = new HashMap<>(10);
        Field[] fields = clazz.getSuperclass().getDeclaredFields();
        handleFields(titleRow, map, fields);
        fields = clazz.getDeclaredFields();
        handleFields(titleRow, map, fields);
        return map;
    }

    /**
     * 把title行转换成为对应的列号和名字
     *
     * @param titleRow title行
     * @param map      字典
     * @param fields   对象的属性们
     */
    private void handleFields(Row titleRow, Map<String, Integer> map, Field[] fields) {
        for (Field field : fields) {
            String fieldName = field.getName();
            for (int i = 0; i < titleRow.getPhysicalNumberOfCells(); i++) {
                Cell cell = titleRow.getCell(i);
                String cellValue = excelUtils.getCellValue(cell);
                if (cellValue.toLowerCase().contains(fieldName.toLowerCase())) {
                    map.put(cellValue, i);
                }
            }
        }
    }

    /**
     * 转换sheet为实体类集合
     *
     * @param sheet     sheet对象
     * @param sheetName sheet的名字（英文部分)
     * @return 实体类集合
     */
    @SneakyThrows
    private List<? extends BaseEntity> handleSheet(Sheet sheet, String sheetName) {
        List<? extends BaseEntity> entities = new ArrayList<>();
        // 获取当前Sheet对应的对象值
        String fullClassName = getFullClassName(sheetName);
        Class<?> clazz = Class.forName(fullClassName);
        // 获取第一行即表头
        Row titleRow = sheet.getRow(0);
        Map<String, Integer> entityMap = getEntityAttributeMap(titleRow, clazz);
        // 移出第一行便于后续的遍历
        sheet.removeRow(titleRow);
        for (Row row : sheet) {
            handleRow(entities, row, clazz, entityMap, row.getRowNum());
        }
        return entities;
    }

    @SneakyThrows
    private void handleRow(List<? extends BaseEntity> entities, Row row, Class<?> clazz, Map<String, Integer> entityMap, int rowNo) {
        // 实例化对象，属于BaseEntity的子类
        Object o = clazz.newInstance();
        Field[] fields = clazz.getSuperclass().getDeclaredFields();
        for (Field field : fields) {
            // 属性名
            String name = field.getName();
            Integer index = entityMap.get(name);
            String cellValue = excelUtils.getCellValue(row.getCell(index));
            // 设置属性值
            setAttributeValue(o, field, cellValue, rowNo);
        }
        // 这样添加错误
        entities.add(o);
    }

    /**
     * 设置值到属性中
     *
     * @param object    实体
     * @param field     属性
     * @param cellValue 值
     */
    @SneakyThrows
    private void setAttributeValue(Object object, Field field, String cellValue, int index) {
        field.setAccessible(true);
        Class<?> clazz = field.getType();
        if (clazz.isEnum()) {
            log.debug("handle enum type");
            // 此处的o是枚举
            Method method = clazz.getMethod("fromValue", String.class);
            field.set(object, method.invoke(null, cellValue));
        } else if (clazz.equals(String.class)) {
            log.debug("handle string type");
            field.set(object, cellValue);
        } else if (clazz.equals(Integer.class)) {
            log.debug("handle integer type");
        } else if (clazz.equals(Element.class)) {
            /*
             * 也需要特别处理，因为可能存在Element没有读取到的情况
             */
            log.debug("handle element type");
        } else if (clazz.equals(List.class)) {
            /*
             * 特别处理LIST，因为有多种类型
             * values : java.util.List : java.util.List<java.lang.Double>
             * points : java.util.List : java.util.List<com.philosophy.base.common.Pair<java.lang.Integer, java.lang.Integer>>
             * signals : java.util.List : java.util.List<java.util.Map<java.lang.String, java.lang.String>>
             * locators : java.util.List : java.util.List<java.util.Map<java.lang.String, java.lang.String>>
             * params : java.util.List : java.util.List<java.lang.String>
             * positions : java.util.List : java.util.List<java.util.Map<com.chinatsp.code.enumeration.PositionEnum, java.lang.Integer>>
             * elementAttributes : java.util.List : java.util.List<com.chinatsp.code.enumeration.ElementAttributeEnum>
             */
            log.debug("handle list type");
        } else if (clazz.equals(Boolean.class)) {
            log.debug("handle boolean type");
        } else if (clazz.equals(Long.class)) {
            log.debug("handle long type");
        } else if (clazz.equals(Double.class)) {
            log.debug("handle double type");
        }
    }

    /**
     * 从文件读取实体
     *
     * @param path 文件路径
     * @return 实体字典
     */
    public Map<String, List<? extends BaseEntity>> readEntity(Path path) {
        Map<String, List<? extends BaseEntity>> map = new HashMap<>(10);
        Map<String, Sheet> sheetMap = readExcel(path);
        log.info("sheetMap size is {}", sheetMap.size());
        for (Map.Entry<String, Sheet> entry : sheetMap.entrySet()) {
            String className = entry.getKey();
            log.debug("handle {}", className);
            Sheet sheet = entry.getValue();
            List<? extends BaseEntity> classes = handleSheet(sheet, className);
            map.put(className, classes);
        }
        return map;
    }

}
