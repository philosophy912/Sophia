package com.chinatsp.automation.impl.utils;


import com.chinatsp.automation.api.IConstant;
import com.chinatsp.automation.entity.actions.CanAction;
import com.chinatsp.automation.entity.actions.ScreenAction;
import com.chinatsp.automation.entity.base.BaseEntity;
import com.chinatsp.automation.entity.base.FunctionEntity;
import com.chinatsp.automation.entity.base.TestCase;
import com.chinatsp.automation.entity.compare.CanCompare;
import com.chinatsp.automation.entity.compare.Compare;
import com.chinatsp.automation.entity.compare.Function;
import com.chinatsp.automation.entity.testcase.Receive;
import com.chinatsp.automation.entity.testcase.Send;
import com.chinatsp.automation.entity.testcase.Cluster;
import com.philosophy.base.common.Pair;
import com.philosophy.base.common.Reflect;
import com.philosophy.base.common.Triple;
import com.philosophy.base.util.StringsUtils;
import com.philosophy.character.util.CharUtils;
import com.philosophy.excel.utils.ExcelUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.chinatsp.dbc.api.IConstant.BLANK;
import static com.chinatsp.dbc.api.IConstant.YES;
import static com.chinatsp.dbc.api.IConstant.YES_CHINESE;


/**
 * @author lizhe
 * @Description 工具类： 作用是给各个Component调用，使用@Component注解，加入IOC容器中
 */
@Slf4j
@Component
public class ReaderUtils implements IConstant {

    private final ExcelUtils excelUtils = new ExcelUtils();

    /**
     * 通过反射对于object来设值
     *
     * @param base   Class对象
     * @param object 要设值的类
     * @param map    读取出来的内容
     */
    private void setEntity(Class<?> base, Object object, Map<String, Integer> map, Row row) {
        Field[] fields = base.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            // 过滤掉messageId
            if ("messageId".equalsIgnoreCase(field.getName())) {
                continue;
            }
            Integer index = map.get(field.getName().toLowerCase());
            String setMethod = "set" + CharUtils.upperCase(field.getName());
            log.debug("setMethod is {}", setMethod);
            // 只有两种类型String和Integer
            setValue(field, object, setMethod, index, row);
        }
    }

    /**
     * 发现指定测Class对象
     *
     * @param origin 当前的类
     * @param target 要查找的类对象
     */
    private Class<?> findBaseClass(Object origin, Class<?> target) {
        Class<?> base = origin.getClass();
        while (base != target) {
            base = base.getSuperclass();
        }
        return base;
    }

    /**
     * 针对不同的属性进行设值
     *
     * @param field     属性
     * @param object    对象
     * @param setMethod 设值方法名
     * @param index     列号
     */
    private void setValue(Field field, Object object, String setMethod, Integer index, Row row) {
        int rowNum = row.getRowNum();
        String fieldName = field.getName();
        log.debug("index = {}", index);
        String cellValue = getCellValue(row, index);
        // 只有两种类型String和Integer
        if (field.getType() == Integer.class) {
            if (!StringsUtils.isEmpty(cellValue)) {
                try {
                    int value = Integer.parseInt(cellValue);
                    Reflect.execute(object, setMethod, new Class[]{Integer.class}, new Integer[]{value});
                } catch (NumberFormatException e) {
                    log.error(e.getMessage());
                    String error = "第" + rowNum + "行的[" + fieldName + "]列的值填写错误，只允许填写数字, 当前填写的是[" + cellValue + "]";
                    throw new RuntimeException(error);
                }
            }
        } else if (field.getType() == Integer[].class) {
            if (!StringsUtils.isEmpty(cellValue)) {
                String[] rgbStr = cellValue.split(CROSSING);
                Integer[] rgbInt = new Integer[rgbStr.length];
                for (int i = 0; i < rgbStr.length; i++) {
                    try {
                        rgbInt[i] = Integer.parseInt(rgbStr[i]);
                    } catch (NumberFormatException e) {
                        log.error(e.getMessage());
                        String error = "第" + rowNum + "行的[" + fieldName + "]列的值填写错误，请以x-y-width-height的方式填写";
                        throw new RuntimeException(error);
                    }
                }
                Reflect.execute(object, setMethod, new Class[]{Integer[].class}, new Integer[][]{rgbInt});
            }
        } else if (field.getType() == String.class) {
            Reflect.execute(object, setMethod, new Class[]{String.class}, new String[]{cellValue});
        } else if (field.getType() == Boolean.class) {
            if (cellValue.equalsIgnoreCase(YES) || cellValue.equalsIgnoreCase(YES_CHINESE)) {
                Reflect.execute(object, setMethod, new Class[]{Boolean.class}, new Boolean[]{true});
            }
        } else if (field.getType() == Double.class) {
            if (!StringsUtils.isEmpty(cellValue)) {
                try {
                    double value = Double.parseDouble(cellValue);
                    Reflect.execute(object, setMethod, new Class[]{Double.class}, new Double[]{value});
                } catch (NumberFormatException e) {
                    log.error(e.getMessage());
                    String error = "第" + rowNum + "行的[" + fieldName + "]列的值填写错误，只允许填写数字,当前填写的是[" + cellValue + "]";
                    throw new RuntimeException(error);
                }
            }
        } else if (field.getType() == List.class) {
            //单独处理，需要知道子类型
            Type genericType = field.getGenericType();
            if (genericType.getTypeName().contains(Pair.class.getName())) {
                List<Pair<String, String>> value = getSpiltValues(cellValue);
                Reflect.execute(object, setMethod, new Class[]{List.class}, new List[]{value});
            } else if (genericType.getTypeName().contains(Triple.class.getName())) {
                List<Triple<String, String, String[]>> value = getSpiltTripleValues(cellValue);
                Reflect.execute(object, setMethod, new Class[]{List.class}, new List[]{value});
            } else {
                if (genericType instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) genericType;
                    Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
                    if (genericClazz == String.class) {
                        List<String> value = getValue(cellValue);
                        Reflect.execute(object, setMethod, new Class[]{List.class}, new List[]{value});
                    }
                }
            }
        }
    }

    /**
     * 排除属性
     *
     * @param content 属性名
     * @param strings 要排除的属性名数组
     * @return 真假
     */
    private boolean excludeParam(String content, String... strings) {
        for (String flag : strings) {
            if (content.equalsIgnoreCase(flag)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 单独处理position对象
     *
     * @param object Compare对象
     * @param map    内容Map
     */
    private void handlePosition(Compare object, Map<String, Integer> map, Row row) {
        int rowNum = row.getRowNum();
        Integer index = map.get(POSITION.toLowerCase());
        List<String> values = getValue(getCellValue(row, index));
        int size = values.size();
        Integer[] x = new Integer[size];
        Integer[] y = new Integer[size];
        Integer[] width = new Integer[size];
        Integer[] height = new Integer[size];
        for (int i = 0; i < size; i++) {
            String[] point = values.get(i).split(CROSSING);
            String err = "x";
            try {
                x[i] = Integer.parseInt(point[0].trim());
                err = "y";
                y[i] = Integer.parseInt(point[1].trim());
                err = "width";
                width[i] = Integer.parseInt(point[2].trim());
                err = "height";
                height[i] = Integer.parseInt(point[3].trim());
            } catch (NumberFormatException e) {
                log.error(e.getMessage());
                String error = "第" + rowNum + "行的[position]列的值[" + err + "]填写错误，只允许填写数字";
                throw new RuntimeException(error);
            }
        }
        String xMethod = "set" + CharUtils.upperCase(X);
        String yMethod = "set" + CharUtils.upperCase(Y);
        String widthMethod = "set" + CharUtils.upperCase(WIDTH);
        String heightMethod = "set" + CharUtils.upperCase(HEIGHT);
        Reflect.execute(object, xMethod, new Class[]{Integer[].class}, new Integer[][]{x});
        Reflect.execute(object, yMethod, new Class[]{Integer[].class}, new Integer[][]{y});
        Reflect.execute(object, widthMethod, new Class[]{Integer[].class}, new Integer[][]{width});
        Reflect.execute(object, heightMethod, new Class[]{Integer[].class}, new Integer[][]{height});
    }

    /**
     * 设值Compare对象
     *
     * @param object 对象
     * @param map    字段行号对应字典
     * @param row    行对象
     */
    private void setCompare(Compare object, Map<String, Integer> map, Row row) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            String setMethod = "set" + CharUtils.upperCase(name);
            log.debug("setMethod is {}", setMethod);
            // 排除x, y, width, height，这个需要单独处理
            if (!excludeParam(name, X, Y, WIDTH, HEIGHT)) {
                Integer index = map.get(field.getName().toLowerCase());
                setValue(field, object, setMethod, index, row);
            }
        }
        handlePosition(object, map, row);
    }

    /**
     * 是否同时包含字符
     *
     * @param content 字符串
     * @param strings 要包含的字符串集合
     * @return 真假
     */
    private boolean isContains(String content, String... strings) {
        for (String str : strings) {
            if (content.contains(str.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据列号获取值，如果为空的时候设置NULL
     *
     * @param row   行对象
     * @param index 列号
     * @return 获取的值
     */
    public String getCellValue(Row row, int index) {
        Cell cell = row.getCell(index);
        if (cell != null) {
            return excelUtils.getCellValue(cell).trim();
        } else {
            return NULL;
        }
    }

    /**
     * 根据分隔符来分割字符串
     * 并去掉空白行
     * 如：
     * CONDITION=Power_IGN \n
     * ACTION=VCU_12VBattChrgFlt_Sts_CORRECT \n
     * CONDITION=iBCM_12VBattCpLowWarn_NO_WARNING \n
     *
     * @param content 内容
     * @param spilt   分隔符（一般为换行符\n)
     * @return <br>
     * 1. CONDITION=Power_IGN
     * 2. ACTION=VCU_12VBattChrgFlt_Sts_CORRECT
     * 3. CONDITION=iBCM_12VBattCpLowWarn_NO_WARNING
     */
    public List<String> getValueBySpilt(String content, String spilt) {
        String[] contents = content.split(spilt);
        List<String> values = Arrays.asList(contents);
        // 去掉空白内容
        values.stream().filter(Objects::nonNull).collect(Collectors.toList()).removeIf(NULL::equals);
        return values;
    }

    public List<String> getValue(String content) {
        return getValueBySpilt(content, NEXT_LINE);
    }

    /**
     * 根据指定的分隔符读取内容（本方法联合getValueBySpilt使用）
     * 1. CONDITION=Power_IGN
     * 2. ACTION=VCU_12VBattChrgFlt_Sts_CORRECT
     * 3. CONDITION=iBCM_12VBattCpLowWarn_NO_WARNING
     *
     * @param contents 内容
     * @param spilt    分隔符，一般用=号
     * @return <br>
     * 1. CONDITION, Power_IGN
     * 2. ACTION, VCU_12VBattChrgFlt_Sts_CORRECT
     * 3. CONDITION, iBCM_12VBattCpLowWarn_NO_WARNING
     */
    public List<Pair<String, String>> getValuesBySpilt(List<String> contents, String spilt) {
        List<Pair<String, String>> values = new LinkedList<>();
        for (String s : contents) {
            if (!StringsUtils.isEmpty(s)) {
                String[] strings = s.split(spilt);
                if (strings.length == 1) {
                    values.add(new Pair<>(strings[0].trim(), null));
                } else {
                    values.add(new Pair<>(strings[0].trim(), strings[1].trim()));
                }
            }
        }
        return values;
    }

    /**
     * 简化版的getValuesBySpilt， 默认分隔符分别是=
     *
     * @param contents 内容
     * @return 列表
     */
    public List<Pair<String, String>> getValues(List<String> contents) {
        return getValuesBySpilt(contents, EQUAL);
    }


    /**
     * 简化版的getValuesBySpilt， 两个默认分隔符分别是\n和=
     *
     * @param content 内容
     * @return 列表
     */
    public List<Pair<String, String>> getSpiltValues(String content) {
        return getValues(getValue(content));
    }

    private String[] convertCommaList(String param) {
        if (param.contains(COMMA)) {
            return param.split(COMMA);
        } else if (param.contains(COMMA_CHINESE)) {
            return param.split(COMMA_CHINESE);
        } else {
            throw new RuntimeException("only support split character [" + COMMA + ", " + COMMA_CHINESE + "]");
        }
    }

    /**
     * 处理函数带参数的部分，以#分割函数参数， 以中文或者英文的逗号分割参数
     *
     * @param content 内容
     * @return 列表
     */
    public List<Triple<String, String, String[]>> getSpiltTripleValues(String content) {
        List<String> contents = getValue(content);
        List<Triple<String, String, String[]>> values = new LinkedList<>();
        for (String s : contents) {
            if (!StringsUtils.isEmpty(s)) {
                String[] strings = s.split(EQUAL);
                if (strings.length == 1) {
                    values.add(new Triple<>(strings[0].trim(), null, null));
                } else {
                    String function = strings[1].trim();
                    if (function.contains(LEFT_BRACKET) && function.contains(RIGHT_BRACKET)) {
                        String[] functionPart = function.replace(RIGHT_BRACKET, NULL).replace(LEFT_BRACKET, HASH3).trim().split(HASH3);
                        String[] params = convertCommaList(functionPart[1].replace("\"", NULL).replace("“", NULL));
                        values.add(new Triple<>(strings[0].trim(), functionPart[0], params));
                    } else if (function.contains(LEFT_BRACKET_CHINESE) && function.contains(RIGHT_BRACKET_CHINESE)) {
                        String[] functionPart = function.replace(RIGHT_BRACKET_CHINESE, NULL).replace(LEFT_BRACKET_CHINESE, HASH3).trim().split(HASH3);
                        String[] params = convertCommaList(functionPart[1].replace("\"", NULL).replace("“", NULL));
                        values.add(new Triple<>(strings[0].trim(), functionPart[0], params));
                    } else {
                        values.add(new Triple<>(strings[0].trim(), strings[1].trim(), null));
                    }
                }
            }
        }
        return values;

    }

    /**
     * 设值BaseEntity部分
     *
     * @param object 对象
     * @param row    一行数据
     * @param map    字段行号对应字典
     */
    @SneakyThrows
    public void setBaseEntity(BaseEntity object, Map<String, Integer> map, Row row) {
        // 查找到顶层的BaseEntity
        Class<?> base = findBaseClass(object, BaseEntity.class);
        setEntity(base, object, map, row);
    }


    /**
     * 设值unctionEntity部分
     *
     * @param object 对象
     * @param row    一行数据
     * @param map    字段行号对应字典
     */
    public void setFunctionEntity(FunctionEntity object, Map<String, Integer> map, Row row) {
        // 查找到顶层的BaseEntity
        Class<?> base = findBaseClass(object, FunctionEntity.class);
        setEntity(base, object, map, row);
    }


    /**
     * 设值BaseTestCaseEntity部分
     *
     * @param object 对象
     * @param map    字段行号对应字典
     * @param row    行对象
     */
    @SneakyThrows
    public void setTestCase(TestCase object, Map<String, Integer> map, Row row) {
        // 查找到顶层的TestCase
        Class<?> base = findBaseClass(object, TestCase.class);
        setEntity(base, object, map, row);
    }

    /**
     * 设值Cluster部分
     *
     * @param object 对象
     * @param map    字段行号对应字典
     * @param row    行对象
     */
    public void setCluster(Cluster object, Map<String, Integer> map, Row row) {
        // 查找到顶层的BaseTestCaseEntity
        Class<?> base = findBaseClass(object, Cluster.class);
        Field[] fields = base.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String setMethod = "set" + CharUtils.upperCase(field.getName());
            log.debug("setMethod is {}", setMethod);
            // 只有两种类型
            if (field.getType() == Compare.class) {
                Compare compare = new Compare();
                setCompare(compare, map, row);
                // 设置compare
                Reflect.execute(object, setMethod, new Class[]{Compare.class}, new Compare[]{compare});
            } else if (field.getType() == List.class) {
                Integer index = map.get(field.getName().toLowerCase());
                // 只有两种类型String和Integer
                setValue(field, object, setMethod, index, row);
            }
        }
    }

    /**
     * 设置Function部分
     *
     * @param object 对象
     * @param map    字段行号对应字典
     * @param row    行对象
     */
    public void setFunctions(Function object, Map<String, Integer> map, Row row) {
        Field[] fields = Function.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String setMethod = "set" + CharUtils.upperCase(field.getName());
            log.debug("setMethod is {}", setMethod);
            if (field.getType() == List.class) {
                Integer index = map.get(field.getName().toLowerCase());
                // 只有两种类型String和Integer
                setValue(field, object, setMethod, index, row);
            }
        }
    }

    /**
     * 设值AirConditionSend部分
     * 非常特殊，单独处理
     *
     * @param airConditionSend 对象
     * @param map              字段行号对应字典
     * @param row              行对象
     */
    @SneakyThrows
    public void setAirConditionSend(Send airConditionSend, Map<String, Integer> map, Row row) {
        String cellValue = getCellValue(row, map.get(CAN_COMPARES.toLowerCase()));
        List<String> contents = getValue(cellValue);
        List<CanCompare> canCompares = new ArrayList<>();
        for (String content : contents) {
            log.debug("can compare content is {}", content);
            CanCompare compare = new CanCompare();
            String[] params = content.split(EQUAL);
            int length = params.length;
            if (length < 3) {
                throw new RuntimeException("至少有messageId, signalName, value三个值");
            } else {
                String msgId = params[0].trim();
                log.debug("msg is {}", msgId);
                String signalName = params[1].trim();
                log.debug("signalName is {}", signalName);
                String value = params[2].trim();
                log.debug("value is {}", value);
                compare.setMessageId(msgId);
                compare.setSignalName(signalName);
                compare.setSignalValue(value);
                if (length >= 4) {
                    String frameCount = params[3].trim();
                    log.debug("frameCount is {}", frameCount);
                    try {
                        compare.setFrameCount(Integer.parseInt(frameCount));
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("frameCount 需要填写成正整数");
                    }
                }
                if (length == 5) {
                    String exact = params[4];
                    log.debug("exact is {}", exact);
                    boolean condition = NO.equalsIgnoreCase(exact) || ENG_NO.equalsIgnoreCase(exact);
                    if (!StringsUtils.isEmpty(exact) && condition) {
                        compare.setExact(false);
                    }
                }
            }
            canCompares.add(compare);
        }
        airConditionSend.setCanCompares(canCompares);
    }


    /**
     * 设值canActions和AirConditionReceive
     *
     * @param object 对象
     * @param map    字段行号对应字典
     * @param row    行对象
     */
    public void setActions(Object object, Map<String, Integer> map, Row row) {
        Class<?> clazz = object.getClass();
        if (clazz == CanAction.class || clazz == Receive.class) {
            setEntity(clazz, object, map, row);
        }
    }

    /**
     * 设值ScreenActions
     *
     * @param object 对象
     * @param map    字段行号对应字典
     */
    public void setScreenActions(ScreenAction object, Map<String, Integer> map, Row row) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            String filedName = field.getName();
            Integer index = map.get(filedName.toLowerCase());
            String setMethod = "set" + CharUtils.upperCase(filedName);
            log.debug("setMethod is {}", setMethod);
            if (filedName.equalsIgnoreCase(POSITION)) {
                String cellValue = getCellValue(row, index);
                List<Pair<String, String>> values = getValuesBySpilt(getValue(cellValue), CROSSING);
                Reflect.execute(object, setMethod, new Class[]{List.class}, new List[]{values});
            } else {
                setValue(field, object, setMethod, index, row);
            }
        }
    }

    public Sheet getSpecificSheet(Workbook workbook, String... keyWords) {
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            int count = 0;
            Sheet sheet = workbook.getSheetAt(i);
            log.debug("sheet name = {}", sheet.getSheetName());
            int maxRow = sheet.getPhysicalNumberOfRows();
            log.debug("maxRow = {}", maxRow);
            if (maxRow > 0) {
                Row row = sheet.getRow(0);
                if (row != null) {
                    for (Cell cell : row) {
                        String value = excelUtils.getCellValue(cell).toLowerCase();
                        log.debug("cell value = {}", value);
                        log.debug("{} contains one of {}", value, Arrays.toString(keyWords));
                        if (isContains(value, keyWords)) {
                            count++;
                        }
                    }
                }
            }
            if (count == keyWords.length) {
                return sheet;
            }
        }
        String e = Arrays.toString(keyWords) + " is not found in any sheet, please check standard template excel file";
        throw new RuntimeException(e);
    }


}
