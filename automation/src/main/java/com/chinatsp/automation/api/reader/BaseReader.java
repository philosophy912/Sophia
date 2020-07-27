package com.chinatsp.automation.api.reader;

import com.chinatsp.automation.entity.base.BaseEntity;
import com.chinatsp.automation.entity.compare.Compare;
import com.chinatsp.automation.impl.utils.ReaderUtils;
import com.philosophy.base.util.StringsUtils;
import com.philosophy.excel.utils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.chinatsp.automation.api.IConstant.COMPARE;
import static com.chinatsp.automation.api.IConstant.HEIGHT;
import static com.chinatsp.automation.api.IConstant.MESSAGE_ID;
import static com.chinatsp.automation.api.IConstant.WIDTH;
import static com.chinatsp.automation.api.IConstant.X;
import static com.chinatsp.automation.api.IConstant.Y;


/**
 * @author lizhe
 * @date 2020/5/26 16:47
 **/
@Slf4j
public abstract class BaseReader {

    protected final ExcelUtils excelUtils = new ExcelUtils();
    protected final ReaderUtils readerUtils = new ReaderUtils();


    protected Map<String, Integer> getTitles(Row row) {
        String regEx = "[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";
        Map<String, Integer> map = new HashMap<>();
        for (Cell cell : row) {
            String name = excelUtils.getCellValue(cell)
                    .replaceAll("[\u4e00-\u9fa5]", "")
                    .replaceAll("\\d+", "")
                    .replaceAll(regEx, "")
                    .toLowerCase()
                    .trim();
            if (!StringsUtils.isEmpty(name)) {
                map.put(name, cell.getColumnIndex());
            }
        }
        return map;
    }

    protected boolean skipFields(String name, String... strings) {
        for (String s : strings) {
            if (name.equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }

    private List<Field> findBaseClass(Class<?> base) {
        List<Field> fields = new ArrayList<>(Arrays.asList(base.getDeclaredFields()));
        while (base != BaseEntity.class) {
            base = base.getSuperclass();
            fields.addAll(Arrays.asList(base.getDeclaredFields()));
        }
        return fields;
    }


    protected void checkMap(Map<String, Integer> map, Class<? extends BaseEntity> clazz, String sheetName) {
        List<Field> fields = findBaseClass(clazz);
        for (Field field : fields) {
            String name = field.getName().toLowerCase();
            log.debug("field name is {}", name);
            switch (name) {
                case MESSAGE_ID:
                    log.debug("skip messageId check");
                    break;
                case COMPARE:
                    for (Field fieldCompare : Compare.class.getDeclaredFields()) {
                        String compareFieldName = fieldCompare.getName().toLowerCase();
                        log.debug("compareFieldName = {}", compareFieldName);
                        if (compareFieldName.equalsIgnoreCase(X) || compareFieldName.equalsIgnoreCase(Y)
                                || compareFieldName.equalsIgnoreCase(WIDTH) || compareFieldName.equalsIgnoreCase(HEIGHT)) {
                            log.debug("skip x, y, width, height check");
                        } else {
                            checkMap(map, compareFieldName, sheetName);
                        }
                    }
                    break;
                default:
                    checkMap(map, name, sheetName);
            }
        }
    }

    private void checkMap(Map<String, Integer> map, String name, String sheetName) {
        if (map.get(name) == null) {
            String e = "[" + name + "] is not found in sheet[" + sheetName + "], please check it";
            throw new RuntimeException(e);
        }
    }
}
