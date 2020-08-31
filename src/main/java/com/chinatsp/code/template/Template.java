package com.chinatsp.code.template;

import com.chinatsp.code.entity.actions.RelayAction;
import com.chinatsp.code.enumeration.TestCaseTypeEnum;
import com.philosophy.base.util.ClazzUtils;
import com.philosophy.base.util.FilesUtils;
import com.philosophy.character.util.CharUtils;
import com.philosophy.excel.utils.ExcelUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    private final ExcelUtils excelUtils = new ExcelUtils();
    private static final Map<String, String> map;
    private static final Map<String, String> classMap;

    static {
        map = new HashMap<>();
        map.put("id", "序号");
        map.put("name", "名字/函数名");
        map.put("comment", "描述");
        map.put("batteryType", "电源类型");
        map.put("batteryOperationType", "电源操作类型");
        map.put("values", "电源操作值");
        map.put("repeatTimes", "重复次数");
        map.put("curveFile", "电压曲线文件");
        map.put("element", "元素名");
        map.put("slideTimes", "滑动次数");
        map.put("relayOperationType", "继电器操作类型");
        map.put("channelIndex", "继电器通道");
        map.put("operationActionType", "操作类型");
        map.put("screenIndex", "屏幕序号");
        map.put("points", "坐标点");
        map.put("continueTimes", "持续时间");
        map.put("deviceTpeEnum", "屏幕类型");
        map.put("count", "截图张数");
        map.put("imageName", "截图名称");
        map.put("isArea", "是否区域截图");
        map.put("signals", "信号名与值");
        map.put("locators", "元素定位符");
        map.put("params", "函数参数");
        map.put("messageId", "信号ID");
        map.put("signalName", "信号名");
        map.put("expectValue", "期望值");
        map.put("exact", "是否精确对比");
        map.put("elementCompareType", "元素对比类型");
        map.put("timeout", "超时时间");
        map.put("compareType", "图片对比方式");
        map.put("templateLight", "模板亮图");
        map.put("templateDark", "模板暗图");
        map.put("positions", "比较区域");
        map.put("similarity", "相似度");
        map.put("isGray", "是否灰度对比");
        map.put("threshold", "灰度二值化阈值");
        map.put("origin", "原始信息");
        map.put("target", "目标信息");
        map.put("elementAttributes", "元素属性");
        map.put("testCaseType", "测试用例类型");
        map.put("moduleName", "模块名");
        map.put("preConditionDescription", "前置条件描述");
        map.put("stepsDescription", "操作步骤描述");
        map.put("expectDescription", "期望结果描述");
        classMap = new HashMap<>();
        classMap.put("TestCase", "测试用例");
        classMap.put("ScreenShotAction", "截图操作");
        classMap.put("ScreenOperationAction", "屏幕操作");
        classMap.put("ElementAction", "元素操作");
        classMap.put("RelayAction", "继电器操作");
        classMap.put("BatteryAction", "电源操作");
        classMap.put("Information", "信息保存");
        classMap.put("Can", "Can信号");
        classMap.put("Element", "安卓元素");
        classMap.put("CanCompare", "CAN信号对比");
        classMap.put("ImageCompare", "图片对比");
        classMap.put("InformationCompare", "信息对比");
        classMap.put("ElementCompare", "Android元素对比");
        classMap.put("Common", "公共函数");
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
        Class clazz = Class.forName(className);
        if (!Modifier.isAbstract(clazz.getModifiers())) {
            Object object = clazz.newInstance();
            Field[] fields = object.getClass().getSuperclass().getDeclaredFields();
            for (Field field : fields) {
                String name = field.getName();
                String upperName = CharUtils.upperCase(name);
                log.debug("field name is {}", name);
                titles.add(upperName + "\n" + map.get(name));
            }
            fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                String name = field.getName();
                String upperName = CharUtils.upperCase(name);
                log.debug("field name is {}", name);
                titles.add(upperName + "\n" + map.get(name));
            }
        }
        return titles;
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


    private void setSheet(Sheet sheet, CellStyle cellStyle, String className) {
        Row row = sheet.createRow(0);
        List<String> titles = getTitles(className);
        for (int i = 0; i < titles.size(); i++) {
            Cell cell = row.createCell(i);
            String content = titles.get(i);
            cell.setCellValue(content);
            cell.setCellStyle(cellStyle);
            sheet.setColumnWidth(i, getWidth(content));
        }
        // 根据
    }


    /**
     * 创建automotive的模板文件
     *
     * @param path 文件
     */
    @SneakyThrows
    public void CreateTemplateExcelFile(Path path) {
        // 确保path不存在
        if (Files.exists(path)) {
            FilesUtils.deleteFiles(path);
        }
        Workbook workbook = excelUtils.openWorkbook(path);
        String PACKAGE_NAME = "com.chinatsp.code.entity";
        List<String> classes = ClazzUtils.getClazzName(PACKAGE_NAME, true);
        classes.remove("com.chinatsp.code.entity.BaseEntity");
        // 设置单元格内容自动换行
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setWrapText(true);
        for (String className : classes) {
            String[] splits = className.split("\\.");
            String sheetName = splits[splits.length - 1];
            sheetName = CharUtils.upperCase(sheetName);
            sheetName = sheetName + "(" + classMap.get(sheetName) + ")";
            Sheet sheet = workbook.createSheet(sheetName);
            setSheet(sheet, cellStyle, className);
        }
        workbook.write(Files.newOutputStream(path));
        excelUtils.close(workbook);
    }

    @SneakyThrows
    public static void main(String[] args) {
//        Path path = Paths.get("d:\\template.xlsx");
//        Template template = new Template();
//        template.CreateTemplateExcelFile(path);
        RelayAction relayAction = new RelayAction();
        Field[] fields = relayAction.getClass().getDeclaredFields();
        for(Field field: fields){
            if(field.getType().isEnum()){
                Class enumClass = field.getType();
                Method method = enumClass.getMethod("values");
                Object object = method.invoke(enumClass);
                System.out.println(object);
                // 必须要统一，否则无法使用相同方法获取枚举值
                // https://blog.csdn.net/SunFlowerXT/article/details/90035512
            }
        }

    }
}
