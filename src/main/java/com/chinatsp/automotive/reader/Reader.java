package com.chinatsp.automotive.reader;

import com.chinatsp.automotive.beans.ExcelProperty;
import com.chinatsp.automotive.entity.BaseEntity;
import com.chinatsp.automotive.enumeration.ConfigureTypeEnum;
import com.chinatsp.automotive.reader.api.ClassTypeFactory;
import com.chinatsp.automotive.reader.api.IClassType;
import com.chinatsp.automotive.utils.ReaderUtils;
import com.philosophy.base.common.Pair;
import com.philosophy.base.util.StringsUtils;
import com.philosophy.excel.utils.ExcelUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.chinatsp.automotive.utils.Constant.NEXT_LINE;
import static com.chinatsp.automotive.utils.Constant.PACKAGE_NAME;
import static com.chinatsp.automotive.utils.Constant.SPLIT_LEFT_BRACKETS;
import static com.chinatsp.automotive.utils.Constant.SPLIT_RIGHT_BRACKETS;

/**
 * @author lizhe
 * @date 2020/8/28 11:00
 **/
@Service
@Slf4j
public class Reader {
    @Resource
    private ExcelProperty excelProperty;
    @Resource
    private ExcelUtils excelUtils;
    @Resource
    private ClassTypeFactory classTypeFactory;
    @Resource
    private ReaderUtils readerUtils;


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
        Map<String, String> classMap = excelProperty.getClassname();
        for (Map.Entry<String, String> entry : classMap.entrySet()) {
            String fieldName = entry.getKey();
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
            log.trace("handle filed [{}]", fieldName);
            for (int i = 0; i < titleRow.getPhysicalNumberOfCells(); i++) {
                Cell cell = titleRow.getCell(i);
                String cellValue = excelUtils.getCellValue(cell);
                String value = cellValue.split(NEXT_LINE)[0];
                log.trace("the filename value [{}] and cellValue =[{}]", value, cellValue);
                if (value.equalsIgnoreCase(fieldName)) {
                    map.put(fieldName, i);
                    break;
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
        String fullClassName = readerUtils.getFullClassName(sheetName, PACKAGE_NAME);
        Class<?> clazz = Class.forName(fullClassName);
        // 获取第一行即表头
        Row titleRow = sheet.getRow(0);
        Map<String, Integer> entityMap = getEntityAttributeMap(titleRow, clazz);
        // 移出第一行便于后续的遍历
        sheet.removeRow(titleRow);
        log.debug("sheet name is {}", sheetName);
        for (Row row : sheet) {
            try {
                handleRow(entities, row, clazz, entityMap, row.getRowNum());
            } catch (RuntimeException e) {
                if (sheetName.equalsIgnoreCase("TestCase")
                        || sheetName.equalsIgnoreCase("TestCaseSetUp")
                        || sheetName.equalsIgnoreCase("Configure")) {
                    throw new RuntimeException(e);
                } else {
                    log.debug("sheetName is {} and rowNo = {} parse error, message is [{}]", sheetName, row.getRowNum(), e.getMessage());
                }
            }

        }
        return entities;
    }

    /**
     * 处理每行的数据
     *
     * @param entities  实体类集合
     * @param row       行数据
     * @param clazz     类
     * @param entityMap 属性与列对应关系字典
     * @param rowNo     列号
     */
    @SneakyThrows
    private void handleRow(List<BaseEntity> entities, Row row, Class<?> clazz, Map<String, Integer> entityMap, int rowNo) {
        // 实例化对象，属于BaseEntity的子类
        Object o = clazz.newInstance();
        log.debug("class name is [{}]", o.getClass().getSimpleName());
        Field[] fields = clazz.getSuperclass().getDeclaredFields();
        setFieldValue(row, entityMap, rowNo, o, fields);
        fields = clazz.getDeclaredFields();
        setFieldValue(row, entityMap, rowNo, o, fields);
        // 强行转换成BaseEntity类型，后续使用的时候根据实际情况做强制转换
        entities.add((BaseEntity) o);
    }

    /**
     * 根据属性值读取excel每列的内容并填到对象中
     *
     * @param row       行
     * @param entityMap 属性与列对应关系字典
     * @param rowNo     列号
     * @param o         对象
     * @param fields    属性数组
     */
    private void setFieldValue(Row row, Map<String, Integer> entityMap, int rowNo, Object o, Field[] fields) {
        for (Field field : fields) {
            // 属性名
            String name = field.getName();
            Integer index = entityMap.get(name);
            log.debug("handle row[{}] index = [{}]", name, index);
            try {
                String cellValue = excelUtils.getCellValue(row.getCell(index));
                setAttributeValue(o, field, cellValue, rowNo);
            } catch (Exception e) {
                String error = "请检查表" + o.getClass().getSimpleName() + "的" + row.getRowNum() +
                        "行数据填写，若为空请删除该行数据。error[" + e.getMessage() + "]";
                throw new RuntimeException(error);
            }
            // 设置属性值

        }
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
        String className = object.getClass().getSimpleName();
        className = "类[" + className + "]的属性[" + fieldName + "]";
        IClassType classType = classTypeFactory.getClassType(clazz);
        if (null != classType) {
            classType.setValue(object, field, clazz, className, cellValue, index);
        }
    }

    /**
     * 从文件读取实体
     *
     * @param sheetMap Excel读取出来的Sheet
     * @return 实体字典
     */
    private Map<String, List<BaseEntity>> readEntity(Map<String, Sheet> sheetMap) {
        Map<String, List<BaseEntity>> map = new HashMap<>(10);
        log.debug("sheetMap size is {}", sheetMap.size());
        for (Map.Entry<String, Sheet> entry : sheetMap.entrySet()) {
            String className = entry.getKey();
            log.debug("handle className {}", className);
            Sheet sheet = entry.getValue();
            log.debug("sheet name = {}", sheet.getSheetName());
            List<BaseEntity> classes = handleSheet(sheet, className);
            if (classes.size() != 0) {
                map.put(className, classes);
            }
        }
        return map;
    }


    /**
     * 根据Configure表格读取数据
     *
     * @param sheet 表格sheet
     * @return 根据枚举列举数据
     */
    private Map<ConfigureTypeEnum, String> readConfig(Sheet sheet) {
        Map<ConfigureTypeEnum, String> map = new HashMap<>();
        // 去掉标题栏
        sheet.removeRow(sheet.getRow(0));
        for (Row row : sheet) {
            String index = excelUtils.getCellValue(row.getCell(0));
            String name = excelUtils.getCellValue(row.getCell(1)).toLowerCase();
            String description = excelUtils.getCellValue(row.getCell(2));
            String content = excelUtils.getCellValue(row.getCell(3));
            // 检查测试类型是否填写
            if (name.equalsIgnoreCase(ConfigureTypeEnum.TEST_CASE_TYPE.getName())) {
                if (StringsUtils.isEmpty(content)) {
                    String error = "配置(Configure)表中" + ConfigureTypeEnum.TEST_CASE_TYPE.getValue() + "填写错误，" +
                            "仅支持[智能座舱/仪表/中控/HMI/空调屏]";
                    throw new RuntimeException(error);
                } else {
                    if (!(content.equalsIgnoreCase("智能座舱")
                            || content.equalsIgnoreCase("仪表")
                            || content.equalsIgnoreCase("中控")
                            || content.equalsIgnoreCase("HMI")
                            || content.equalsIgnoreCase("空调屏"))) {
                        String error = "配置(Configure)表中" + ConfigureTypeEnum.TEST_CASE_TYPE.getValue() +
                                "填写错误，仅支持[智能座舱/仪表/中控/HMI/空调屏], 当前填写的值为" + content + ".";
                        throw new RuntimeException(error);
                    }
                }
            }
            if (!StringsUtils.isEmpty(content)) {
                if (name.contains("port")) {
                    if (!content.toLowerCase().startsWith("com")) {
                        String error = "第" + Integer.parseInt(index) + "行填写错误，端口号填写错误，当前填写的是" + content;
                        throw new RuntimeException(error);
                    }
                } else if (name.contains("baud_rate")) {
                    try {
                        Integer.parseInt(content);
                    } catch (NumberFormatException e) {
                        String error = "第" + Integer.parseInt(index) + "行填写错误，波特率填写错误，当前填写的是" + content;
                        throw new RuntimeException(error);
                    }
                } else if (name.contains("resolution")) {
                    try {
                        Integer.parseInt(content);
                    } catch (NumberFormatException e) {
                        String error = "第" + Integer.parseInt(index) + "行填写错误，分辨率填写错误，当前填写的是" + content;
                        throw new RuntimeException(error);
                    }
                } else if (name.contains("voltage")) {
                    try {
                        Double.parseDouble(content);
                    } catch (NumberFormatException e) {
                        String error = "第" + Integer.parseInt(index) + "行填写错误，电压填写错误，当前填写的是" + content;
                        throw new RuntimeException(error);
                    }
                }
            }

            map.put(ConfigureTypeEnum.fromValue(description), content);
        }
        return map;
    }

    /**
     * 读取Excel所有内容
     *
     * @param path Excel所在位置
     * @return Excel对应的Sheet对象
     */
    public Pair<Map<String, List<BaseEntity>>, Map<ConfigureTypeEnum, String>> readTestCase(Path path) {
        String configure = "configure";
        Map<String, Sheet> sheetMap = readExcel(path);
        Sheet configureSheet = sheetMap.get(configure);
        sheetMap.remove(configure);
        return new Pair<>(readEntity(sheetMap), readConfig(configureSheet));
    }

}
