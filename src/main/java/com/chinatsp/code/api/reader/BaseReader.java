package com.chinatsp.code.api.reader;

import com.philosophy.base.common.Pair;
import com.philosophy.base.common.Reflect;
import com.philosophy.base.common.Triple;
import com.philosophy.base.util.StringsUtils;
import com.philosophy.character.util.CharUtils;
import com.philosophy.excel.utils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.chinatsp.code.api.Constant.COMMA;
import static com.chinatsp.code.api.Constant.COMMA_CHINESE;
import static com.chinatsp.code.api.Constant.CROSSING;
import static com.chinatsp.code.api.Constant.DOUBLE_QUOTATION;
import static com.chinatsp.code.api.Constant.DOUBLE_QUOTATION_CHINESE;
import static com.chinatsp.code.api.Constant.EQUAL;
import static com.chinatsp.code.api.Constant.HASH3;
import static com.chinatsp.code.api.Constant.LEFT_BRACKET;
import static com.chinatsp.code.api.Constant.LEFT_BRACKET_CHINESE;
import static com.chinatsp.code.api.Constant.NEXT_LINE;
import static com.chinatsp.code.api.Constant.NULL;
import static com.chinatsp.code.api.Constant.RIGHT_BRACKET;
import static com.chinatsp.code.api.Constant.RIGHT_BRACKET_CHINESE;
import static com.chinatsp.dbc.api.IConstant.YES;
import static com.chinatsp.dbc.api.IConstant.YES_CHINESE;

/**
 * @author lizhe
 * @date 2020/8/17 13:23
 **/
@Slf4j
public abstract class BaseReader {

    @Autowired
    private ExcelUtils excelUtils;

    protected void setEntity(Class<?> base, Object object, Map<String, Integer> map, Row row) {
        Field[] fields = base.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Integer index = map.get(field.getName().toLowerCase());
            String setMethod = "set" + CharUtils.upperCase(field.getName());
            log.debug("setMethod is {}", setMethod);
            // 只有两种类型String和Integer
            setValue(field, object, setMethod, index, row);
        }
    }

    /**
     * 针对excel中的每一个单元格做拆分，由于多行的存在，如：
     * CAN=IGN_ON
     * ANDROID=click_main_nav
     * 则返回了列表
     *
     * @param content 单元格内容
     * @return 单元格中每一行内容
     */
    private List<String> spiltLines(String content) {
        String[] contents = content.split(NEXT_LINE);
        List<String> values = Arrays.asList(contents);
        // 去掉空白内容
        values.stream().filter(Objects::nonNull).collect(Collectors.toList()).removeIf(NULL::equals);
        return values;
    }

    /**
     * 把最后的参数转换成
     * 1,2
     *
     * @param param 参数行
     * @return [1, 2]
     */
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
     * 针对每一行内容进行拆分， 如
     * ANDROID=click_main_nav#("1","2")
     *
     * @param line 单元格分割出来的一行数据
     * @return Triple<String, String, String [ ]>
     * 类型，函数名，参数
     */
    private Triple<String, String, String[]> splitOneLine(String line) {
        String[] contents = line.split(EQUAL);
        if (contents.length == 1) {
            return new Triple<>(line.trim(), null, null);
        } else {
            String function = contents[1].trim();
            if (function.contains(LEFT_BRACKET) && function.contains(RIGHT_BRACKET)) {
                String[] functionPart = function.replace(RIGHT_BRACKET, NULL).replace(LEFT_BRACKET, HASH3).trim().split(HASH3);
                String[] params = convertCommaList(functionPart[1].replace(DOUBLE_QUOTATION, NULL).replace(DOUBLE_QUOTATION_CHINESE, NULL));
                return new Triple<>(contents[0].trim(), functionPart[0].trim(), params);
            } else if (function.contains(RIGHT_BRACKET_CHINESE) && function.contains(LEFT_BRACKET_CHINESE)) {
                String[] functionPart = function.replace(RIGHT_BRACKET_CHINESE, NULL).replace(LEFT_BRACKET_CHINESE, HASH3).trim().split(HASH3);
                String[] params = convertCommaList(functionPart[1].replace(DOUBLE_QUOTATION, NULL).replace(DOUBLE_QUOTATION_CHINESE, NULL));
                return new Triple<>(contents[0].trim(), functionPart[0].trim(), params);
            } else {
                return new Triple<>(contents[0].trim(), contents[1].trim(), null);
            }
        }
    }

    /**
     * 针对每一行内容进行拆分，如
     * 622-1
     *
     * @param line 每一行数据
     * @return Pair<String, String>
     * 坐标点X， 坐标点Y
     */
    private Pair<String, String> splitPair(String line) {
        String[] contents = line.split(CROSSING);
        return new Pair<>(contents[0].trim(), contents[1].trim());
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
                List<Pair<String, String>> value = readPairCell(cellValue);
                Reflect.execute(object, setMethod, new Class[]{List.class}, new List[]{value});
            } else if (genericType.getTypeName().contains(Triple.class.getName())) {
                List<Triple<String, String, String[]>> value = readTripleCell(cellValue);
                Reflect.execute(object, setMethod, new Class[]{List.class}, new List[]{value});
            } else {
                if (genericType instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) genericType;
                    Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
                    if (genericClazz == String.class) {
                        List<String> value = spiltLines(cellValue);
                        Reflect.execute(object, setMethod, new Class[]{List.class}, new List[]{value});
                    }
                }
            }
        }
    }

    /**
     * 根据列号获取值，如果为空的时候设置NULL
     *
     * @param row   行对象
     * @param index 列号
     * @return 获取的值
     */
    private String getCellValue(Row row, int index) {

        Cell cell = row.getCell(index);
        if (cell != null) {
            return excelUtils.getCellValue(cell).trim();
        } else {
            return NULL;
        }
    }

    /**
     * 把单元格转换成List
     * ANDROID=click_main_nav#("1","2")
     *
     * @param content 单元格内容
     * @return 列表 ANDROID， click_main_nav， [1, 2]
     */
    private List<Triple<String, String, String[]>> readTripleCell(String content) {
        List<String> contents = spiltLines(content);
        List<Triple<String, String, String[]>> functions = new LinkedList<>();
        contents.forEach(s -> functions.add(splitOneLine(s)));
        return functions;
    }

    /**
     * 把单元格转换成List
     * ANDROID=click_main_nav#("1","2")
     *
     * @param content 单元格内容
     * @return 列表 ANDROID， click_main_nav， [1, 2]
     */
    private List<Pair<String, String>> readPairCell(String content) {
        List<String> contents = spiltLines(content);
        List<Pair<String, String>> pairs = new LinkedList<>();
        contents.forEach(s -> pairs.add(splitPair(s)));
        return pairs;
    }

}
