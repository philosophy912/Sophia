package com.chinatsp.automation.api.checker;

import com.chinatsp.automation.entity.actions.CanAction;
import com.chinatsp.automation.entity.actions.ScreenAction;
import com.chinatsp.automation.entity.base.FunctionEntity;
import com.philosophy.base.common.Pair;
import com.philosophy.base.common.Triple;
import com.philosophy.base.util.StringsUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.chinatsp.automation.api.IConstant.ACTION;
import static com.chinatsp.automation.api.IConstant.CLEAR;
import static com.chinatsp.automation.api.IConstant.CLICK;
import static com.chinatsp.automation.api.IConstant.CONDITION;
import static com.chinatsp.automation.api.IConstant.LOST;
import static com.chinatsp.automation.api.IConstant.PASS;
import static com.chinatsp.automation.api.IConstant.PAUSE;
import static com.chinatsp.automation.api.IConstant.PRESS;
import static com.chinatsp.automation.api.IConstant.RESUME;
import static com.chinatsp.automation.api.IConstant.SCREENSHOT;
import static com.chinatsp.automation.api.IConstant.SERVICE;
import static com.chinatsp.automation.api.IConstant.SLEEP;
import static com.chinatsp.automation.api.IConstant.SLIDE;
import static com.chinatsp.automation.api.IConstant.TEXT;
import static com.chinatsp.automation.api.IConstant.YIELD;

/**
 * @author lizhe
 * @date 2020-05-27 21:53
 */
@Slf4j
public abstract class BaseCheck {


    /**
     * 查找重复的名字
     *
     * @param convertList list
     */
    protected void findDuplicateString(List<Pair<Integer, String>> convertList) {
        Map<String, String> map = new HashMap<>(12);
        convertList.forEach(pair -> {
            String key = pair.getSecond();
            String old = map.get(key);
            if (old != null) {
                map.put(key, old + " & " + pair.getFirst());
            } else {
                map.put(key, "" + pair.getFirst());
            }
        });
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (value.contains("&")) {
                throw new RuntimeException("期望结果函数名[" + key + "]在[" + value + "]中有重名，请仔细检查");
            }
        }
    }

    /**
     * 函数名检查是否正确（python）
     *
     * @param name 函数名
     * @return true：正确 false：不正确
     */
    protected boolean isFunctionCorrect(String name) {
        int max = 255;
        String regex = "[a-zA-Z]+[a-zA-Z0-9_]*";
        boolean isMatch = name.matches(regex);
        if (name.length() > max) {
            return false;
        } else {
            return isMatch;
        }
    }

    protected String getError(String sheetName, Integer index) {
        return sheetName + "中序号为【" + index + "】的";
    }


    /**
     * 检查是否有描写
     *
     * @param descriptions 描述列表
     * @param index        序号
     * @param type         类型
     */
    protected void checkText(List<String> descriptions, int index, String type, String sheetName) {
        if (descriptions == null) {
            String e = getError(sheetName, index) + "的" + type + "没有填写数据，请检查";
            throw new RuntimeException(e);
        } else {
            long count = descriptions.stream().filter(s -> !StringsUtils.isEmpty(s)).count();
            if (count == 0) {
                String e = getError(sheetName, index) + "的" + type + "填写的所有数据都为空，请检查";
                throw new RuntimeException(e);
            }
        }
    }

    private void checkPairCanActions(String type, String functionName, int index, String sheetName, List<CanAction> canActions) {
        if (type.equalsIgnoreCase(CONDITION) || type.equalsIgnoreCase(ACTION)) {
            long count = canActions.stream()
                    .filter(canAction -> canAction.getCategory().equalsIgnoreCase(type)
                            && canAction.getFunctionName().equalsIgnoreCase(functionName)).count();
            if (count != 1) {
                String e = getError(sheetName, index) + type + "的函数[" + functionName + "]" +
                        "没有在" + type + "中找到，请检查";
                throw new RuntimeException(e);
            }
        } else if (type.equalsIgnoreCase(SLEEP)) {
            // 检查SLEEP
            try {
                Float.parseFloat(functionName);
            } catch (NumberFormatException e) {
                String error = getError(sheetName, index) + "SLEEP的时间填写不正确，请检查" + "[" + e.getMessage() + "]";
                throw new RuntimeException(error);
            }
        } else if (type.equalsIgnoreCase(PAUSE)) {
            // 检查PAUSE
            try {
                if (functionName.contains("x") || functionName.contains("X")) {
                    Integer.parseInt(functionName.substring(2), 16);
                } else {
                    Integer.parseInt(functionName);
                }
            } catch (NumberFormatException e) {
                String error = getError(sheetName, index) + "PAUSE的数字填写不正确，请检查" + "[" + e.getMessage() + "]";
                throw new RuntimeException(error);
            }
        }
    }

    private void checkPairScreenActions(String type, String functionName, int index, String sheetName, List<ScreenAction> screenActions) {
        if (Stream.of(CLICK, SLIDE, SLIDE).anyMatch(type::equalsIgnoreCase)) {
            long count = screenActions.stream().filter(screenAction -> screenAction.getCategory().equalsIgnoreCase(type)
                    && screenAction.getFunctionName().equalsIgnoreCase(functionName)).count();
            if (count != 1) {
                String e = getError(sheetName, index) + "函数[" + functionName + "]" + "没有在" + type + "中找到，请检查";
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 检查excel键值对的方式是否正确
     * @param pairs excel键值对内容
     * @param index 序号
     * @param sheetName sheet名称
     * @param canActions sheet动作解析出来的对象
     * @param screenActions sheet屏幕操作解析出来的对象
     */
    protected void checkPairs(List<Triple<String, String, String[]>> pairs, int index, String sheetName, List<CanAction> canActions, List<ScreenAction> screenActions) {
        for (Pair<String, String> pair : pairs) {
            String type = pair.getFirst();
            log.debug("current type is {}", type);
            checkTypes(index, type, CONDITION, ACTION, SLEEP, LOST, PAUSE, RESUME, CLEAR, CLICK, SLIDE, PRESS, SERVICE, YIELD, TEXT, PASS, SCREENSHOT);
            String functionName = pair.getSecond();
            log.debug("function name = {}", functionName);
            checkPairCanActions(type, functionName, index, sheetName, canActions);
            if (null != screenActions) {
                checkPairScreenActions(type, functionName, index, sheetName, screenActions);
            }
        }
    }


    protected void checkTypes(int index, String action, String... types) {
        boolean flag = false;
        for (String type : types) {
            flag = flag || type.equalsIgnoreCase(action);
        }
        if (!flag) {
            throw new RuntimeException("第" + index + "行的" + action + "不正确，只支持[" + Arrays.toString(types) + "]，请检查");
        }
    }

    /**
     * 检查函数名不能重名
     *
     * @param objects param每一行
     */
    protected void checkDuplicateName(List<? extends FunctionEntity> objects) {
        List<Pair<Integer, String>> convertList = new ArrayList<>();
        objects.forEach(object -> convertList.add(new Pair<>(object.getId(), object.getFunctionName())));
        findDuplicateString(convertList);
    }
}
