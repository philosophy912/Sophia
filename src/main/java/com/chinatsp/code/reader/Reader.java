package com.chinatsp.code.reader;

import com.chinatsp.code.beans.ClassNames;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.enumeration.AndroidLocatorTypeEnum;
import com.chinatsp.code.utils.ConvertUtils;
import com.philosophy.base.common.Pair;
import com.philosophy.base.util.ClazzUtils;
import com.philosophy.excel.utils.ExcelUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.chinatsp.code.utils.Constant.CHINESE_YES;
import static com.chinatsp.code.utils.Constant.EQUAL;
import static com.chinatsp.code.utils.Constant.LINE;
import static com.chinatsp.code.utils.Constant.LINUX_NEXT_LINE;
import static com.chinatsp.code.utils.Constant.PACKAGE_NAME;
import static com.chinatsp.code.utils.Constant.SPLIT_LEFT_BRACKETS;
import static com.chinatsp.code.utils.Constant.SPLIT_POINT;
import static com.chinatsp.code.utils.Constant.SPLIT_RIGHT_BRACKETS;
import static com.chinatsp.code.utils.Constant.YES;

/**
 * @author lizhe
 * @date 2020/8/28 11:00
 **/
@Component
@Slf4j
public class Reader {

    private ExcelUtils excelUtils;
    private ClassNames classNames;
    private ConvertUtils convertUtils;

    @Autowired
    public void setExcelUtils(ExcelUtils excelUtils) {
        this.excelUtils = excelUtils;
    }

    @Autowired
    public void setClassNames(ClassNames classNames) {
        this.classNames = classNames;
    }

    @Autowired
    public void setConvertUtils(ConvertUtils convertUtils) {
        this.convertUtils = convertUtils;
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
            log.debug("field name is {}", fieldName);
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                String sheetName = sheet.getSheetName();
                log.debug("sheet name is {}", sheetName);
                String name = sheetName.split(SPLIT_LEFT_BRACKETS)[1].split(SPLIT_RIGHT_BRACKETS)[0];
                log.debug("compare sheet name is {}", name);
                if (name.equalsIgnoreCase(fieldName.toLowerCase())) {
                    map.put(fieldName, sheet);
                }
            }
        }
        log.debug("map size is {}", map.size());
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
            String[] names = className.split(SPLIT_POINT);
            String name = names[names.length - 1];
            if (name.equalsIgnoreCase(sheetName)) {
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
                String value = cellValue.split(LINUX_NEXT_LINE)[0];
                if (value.equalsIgnoreCase(fieldName)) {
                    map.put(fieldName, i);
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
    private List<BaseEntity> handleSheet(Sheet sheet, String sheetName) {
        log.debug("now handle sheet [{}]", sheetName);
        List<BaseEntity> entities = new ArrayList<>();
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
    private void handleRow(List<BaseEntity> entities, Row row, Class<?> clazz, Map<String, Integer> entityMap, int rowNo) {
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
        fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // 属性名
            String name = field.getName();
            Integer index = entityMap.get(name);
            String cellValue = excelUtils.getCellValue(row.getCell(index));
            // 设置属性值
            setAttributeValue(o, field, cellValue, rowNo);
        }
        // 强行转换成BaseEntity类型，后续使用的时候根据实际情况做强制转换
        entities.add((BaseEntity) o);
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
        String fieldName = field.getName();
        log.debug("file name is = {}", fieldName);
        field.setAccessible(true);
        Class<?> clazz = field.getType();
        String className = object.getClass().getName();
        String[] classNames = className.split(SPLIT_POINT);
        className = "类[" + classNames[classNames.length - 1] + "]的属性[" + fieldName + "]";
        if (clazz.isEnum()) {
            log.debug("handle enum type");
            // 此处的o是枚举
            Method method = clazz.getMethod("fromValue", String.class);
            try {
                field.set(object, method.invoke(null, cellValue));
            } catch (Exception e) {
                String error = "第" + index + "行填写错误，请检查" + className + "的值[" + cellValue + "]";
                throw new RuntimeException(error);
            }
        } else if (clazz.equals(String.class)) {
            log.trace("handle string type");
            field.set(object, cellValue);
        } else if (clazz.equals(Integer.class)) {
            log.trace("handle integer type");
            try {
                field.set(object, convertUtils.convertInteger(cellValue));
            } catch (Exception e) {
                String error = "第" + index + "行填写错误，请检查" + className + "的值[" + cellValue + "]";
                throw new RuntimeException(error);
            }
        } else if (clazz.equals(Boolean.class)) {
            log.trace("handle boolean type");
            boolean flag = cellValue.equalsIgnoreCase(YES) || cellValue.equalsIgnoreCase(CHINESE_YES);
            field.set(object, flag);
        } else if (clazz.equals(Long.class)) {
            log.trace("handle long type");
            try {
                field.set(object, convertUtils.convertLong(cellValue));
            } catch (Exception e) {
                String error = "第" + index + "行填写错误，请检查" + className + "的值[" + cellValue + "]";
                throw new RuntimeException(error);
            }
        } else if (clazz.equals(Double.class)) {
            log.trace("handle double type");
            try {
                field.set(object, Double.parseDouble(cellValue));
            } catch (Exception e) {
                String error = "第" + index + "行填写错误，请检查" + className + "的值[" + cellValue + "]";
                throw new RuntimeException(error);
            }
        } else if (clazz.equals(Double[].class)) {
            log.trace("handle double type");
            try {
                field.set(object, convertUtils.convertDoubleArrays(cellValue, LINE));
            } catch (Exception e) {
                String error = "第" + index + "行填写错误，请检查" + className + "的值[" + cellValue + "]";
                throw new RuntimeException(error);
            }
        } else if (clazz.equals(List.class)) {
            /*
             * 特别处理LIST，因为有多种类型
             *  values : java.util.List : <java.lang.Double>
             *  points : java.util.List : <com.philosophy.base.common.Pair<java.lang.Integer, java.lang.Integer>>
             *  signals : java.util.List : <com.philosophy.base.common.Pair<java.lang.String, java.lang.String>>
             *  locators : java.util.List : <java.util.Map<java.lang.String, java.lang.String>>
             *  params : java.util.List : <java.lang.String>
             *  positions : java.util.List : <java.lang.Integer[]>
             *  elementAttributes : java.util.List : <com.chinatsp.code.enumeration.ElementAttributeEnum>
             */
            log.trace("handle list type");
            Type genericType = field.getGenericType();
            String typeName = genericType.getTypeName();
            log.debug("type name = {}", typeName);
            if (typeName.contains(Pair.class.getName())) {
                // 特别注意Pair有两个方式，一个是全Integer，一个是全String
                if (typeName.contains("String")) {
                    try {
                        List<Pair<String, String>> pairs = convertUtils.convertPairStringString(cellValue, EQUAL);
                        field.set(object, pairs);
                    } catch (Exception e) {
                        String error = "第" + index + "行填写错误，请检查" + className + "的值[" + cellValue + "]";
                        throw new RuntimeException(error);
                    }
                } else if (typeName.contains("Integer")) {
                    try {
                        List<Pair<Integer, Integer>> pairs = convertUtils.convertPairIntegerInteger(cellValue, LINE);
                        field.set(object, pairs);
                    } catch (Exception e) {
                        String error = "第" + index + "行填写错误，请检查" + className + "的值[" + cellValue + "]";
                        throw new RuntimeException(error);
                    }
                } else {
                    String error = "can not support type now";
                    throw new RuntimeException(error);
                }
            } else if (typeName.contains(Map.class.getName())) {
                if (typeName.contains("String")) {
                    try {
                        List<Map<AndroidLocatorTypeEnum, String>> mapList = convertUtils.convertMapStringString(cellValue);
                        field.set(object, mapList);
                    } catch (Exception e) {
                        String error = "第" + index + "行填写错误，请检查" + className + "的值[" + cellValue + "]";
                        throw new RuntimeException(error);
                    }
                } else {
                    String error = "can not support type now";
                    throw new RuntimeException(error);
                }
            } else {
                ParameterizedType pt = (ParameterizedType) genericType;
                Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
                if (genericClazz.isEnum()) {
                    // 此处的o是枚举
                    Method method = genericClazz.getMethod("fromValue", String.class);
                    List<String> strings = convertUtils.convertStrings(cellValue);
                    List<Object> lists = new LinkedList<>();
                    try {
                        // 遍历获取枚举
                        for (String s : strings) {
                            lists.add(method.invoke(null, s));
                        }
                    } catch (Exception e) {
                        String error = "第" + index + "行填写错误，请检查" + className + "的值[" + cellValue + "]";
                        throw new RuntimeException(error);
                    }
                    field.set(object, lists);
                } else if (genericClazz == String.class) {
                    List<String> strings;
                    try {
                        strings = convertUtils.convertStrings(cellValue);
                    } catch (Exception e) {
                        String error = "第" + index + "行填写错误，请检查" + className + "的值[" + cellValue + "]";
                        throw new RuntimeException(error);
                    }
                    field.set(object, strings);
                } else if (genericClazz == Integer[].class) {
                    List<Integer[]> integers;
                    try {
                        integers = convertUtils.convertIntegerArrays(cellValue, LINE);
                    } catch (Exception e) {
                        String error = "第" + index + "行填写错误，请检查" + className + "的值[" + cellValue + "]";
                        throw new RuntimeException(error);
                    }
                    field.set(object, integers);

                }
            }
        }
    }

    /**
     * 从文件读取实体
     *
     * @param path 文件路径
     * @return 实体字典
     */
    public Map<String, List<BaseEntity>> readEntity(Path path) {
        Map<String, List<BaseEntity>> map = new HashMap<>(10);
        Map<String, Sheet> sheetMap = readExcel(path);
        log.debug("sheetMap size is {}", sheetMap.size());
        for (Map.Entry<String, Sheet> entry : sheetMap.entrySet()) {
            String className = entry.getKey();
            log.debug("handle className {}", className);
            Sheet sheet = entry.getValue();
            log.debug("sheet name = {}", sheet.getSheetName());
            List<BaseEntity> classes = handleSheet(sheet, className);
            map.put(className, classes);
        }
        return map;
    }

}
