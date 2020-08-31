package com.chinatsp.code.template;

import com.chinatsp.code.beans.ClassAttributes;
import com.chinatsp.code.beans.ClassNames;
import com.chinatsp.code.utils.EnumUtils;
import com.philosophy.base.util.ClazzUtils;
import com.philosophy.base.util.FilesUtils;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 创建Template文件
 *
 * @author lizhe
 * @date 2020/8/31 11:04
 **/
@Component
@Slf4j
public class Template {

    //    private static final Map<String, String> map;
//    private static final Map<String, String> classMap;
    private EnumUtils enumUtils;
    private ExcelUtils excelUtils;
    private ClassAttributes classAttributes;
    private ClassNames classNames;

    @Autowired
    public void setEnumUtils(EnumUtils enumUtils) {
        this.enumUtils = enumUtils;
    }

    @Autowired
    public void setExcelUtils(ExcelUtils excelUtils) {
        this.excelUtils = excelUtils;
    }

    @Autowired
    public void setClassAttributes(ClassAttributes classAttributes) {
        this.classAttributes = classAttributes;
    }

    @Autowired
    public void setClassNames(ClassNames classNames) {
        this.classNames = classNames;
    }

    //    static {
//        // 创建静态的属性的中文名以及Sheet的中文名
//        map = new HashMap<>();
//        map.put("id", "序号");
//        map.put("name", "名字/函数名");
//        map.put("comment", "描述");
//        map.put("batteryType", "电源类型");
//        map.put("batteryOperationType", "电源操作类型");
//        map.put("values", "电源操作值");
//        map.put("repeatTimes", "重复次数");
//        map.put("curveFile", "电压曲线文件");
//        map.put("element", "元素名");
//        map.put("slideTimes", "滑动次数");
//        map.put("relayOperationType", "继电器操作类型");
//        map.put("channelIndex", "继电器通道");
//        map.put("operationActionType", "操作类型");
//        map.put("screenIndex", "屏幕序号");
//        map.put("points", "坐标点");
//        map.put("continueTimes", "持续时间");
//        map.put("deviceTpeEnum", "屏幕类型");
//        map.put("count", "截图张数");
//        map.put("imageName", "截图名称");
//        map.put("isArea", "是否区域截图");
//        map.put("signals", "信号名与值");
//        map.put("locators", "元素定位符");
//        map.put("params", "函数参数");
//        map.put("messageId", "信号ID");
//        map.put("signalName", "信号名");
//        map.put("expectValue", "期望值");
//        map.put("exact", "是否精确对比");
//        map.put("elementCompareType", "元素对比类型");
//        map.put("timeout", "超时时间");
//        map.put("compareType", "图片对比方式");
//        map.put("templateLight", "模板亮图");
//        map.put("templateDark", "模板暗图");
//        map.put("positions", "比较区域");
//        map.put("similarity", "相似度");
//        map.put("isGray", "是否灰度对比");
//        map.put("threshold", "灰度二值化阈值");
//        map.put("origin", "原始信息");
//        map.put("target", "目标信息");
//        map.put("elementAttributes", "元素属性");
//        map.put("testCaseType", "测试用例类型");
//        map.put("moduleName", "模块名");
//        map.put("preConditionDescription", "前置条件描述");
//        map.put("stepsDescription", "操作步骤描述");
//        map.put("expectDescription", "期望结果描述");
//        classMap = new HashMap<>();
//        classMap.put("TestCase", "测试用例");
//        classMap.put("ScreenShotAction", "截图操作");
//        classMap.put("ScreenOperationAction", "屏幕操作");
//        classMap.put("ElementAction", "元素操作");
//        classMap.put("RelayAction", "继电器操作");
//        classMap.put("BatteryAction", "电源操作");
//        classMap.put("Information", "信息保存");
//        classMap.put("Can", "Can信号");
//        classMap.put("Element", "安卓元素");
//        classMap.put("CanCompare", "CAN信号对比");
//        classMap.put("ImageCompare", "图片对比");
//        classMap.put("InformationCompare", "信息对比");
//        classMap.put("ElementCompare", "Android元素对比");
//        classMap.put("Common", "公共函数");
//    }
    @SneakyThrows
    private String getConfigValue(Object object, String name) {
        name = CharUtils.lowerCase(name);
        Method method = object.getClass().getMethod("get" + CharUtils.upperCase(name));
        return (String) method.invoke(object);
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
            log.debug("field name is {}", name);
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
                if (j ==0){
                    cell.setCellValue(i);
                }else{
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
        // 确保path不存在
        if (Files.exists(path)) {
            FilesUtils.deleteFiles(path);
        }
        Workbook workbook = excelUtils.openWorkbook(path);
        String PACKAGE_NAME = "com.chinatsp.code.entity";
        List<String> classes = ClazzUtils.getClazzName(PACKAGE_NAME, true);
        // 去掉了抽象类
        String BASE_ENTITY = "com.chinatsp.code.entity.BaseEntity";
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
            sheetName = CharUtils.upperCase(sheetName);
            String chinese = getConfigValue(classNames, sheetName);
            sheetName = CharUtils.upperCase(chinese) + "(" + sheetName + ")";
            Sheet sheet = workbook.createSheet(sheetName);
            setSheet(sheet, cellStyle, className);
        }
        workbook.write(Files.newOutputStream(path));
        excelUtils.close(workbook);
    }
}
