package com.chinatsp.automation.impl.checker;

import com.chinatsp.automation.api.checker.BaseCheck;
import com.chinatsp.automation.api.checker.IFunctionCheck;
import com.chinatsp.automation.entity.actions.CanAction;
import com.chinatsp.automation.entity.actions.ScreenAction;
import com.chinatsp.automation.entity.compare.Function;
import com.philosophy.base.common.Triple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.chinatsp.automation.api.IConstant.TEXT;
import static com.chinatsp.automation.api.IConstant.YIELD;

/**
 * @author lizhe
 * @date 2020/6/11 11:14
 **/
@Component
@Slf4j
public class FunctionCheck extends BaseCheck implements IFunctionCheck {
    private static final String SHEET_NAME = "Sheet【前置条件】中";

    @Override
    public void check(List<Function> functions, List<CanAction> canActions, List<ScreenAction> screenActions) {
        functions.forEach(function -> {
            String category = function.getCategory();
            List<Triple<String, String, String[]>> actions = function.getActions();
            List<Triple<String, String, String[]>> suites = function.getSuites();
            int index = function.getId();
            log.debug("now check line {}", index);
            int[] yields = checkYield(function, index, category);
            checkSuitePairs(suites, yields[1]);
            checkPairs(actions, index, SHEET_NAME, canActions, screenActions);
            checkPairs(suites, index, SHEET_NAME, canActions, screenActions);
        });
    }

    private void checkYieldIndex(int size, int yieldIndex, int index, String category) {
        if (yieldIndex == -1) {
            String e = "no yield found in category[" + category + "] in row [" + index + "]";
            throw new RuntimeException(e);
        } else if (yieldIndex == 0) {
            String e = "before yield must be some operator in category[" + category + "] in row [" + index + "]";
            throw new RuntimeException(e);
        } else if (yieldIndex == size - 1) {
            String e = "after yield must be some operator in category[" + category + "] in row [" + index + "]";
            throw new RuntimeException(e);
        }
    }

    private int[] checkYield(Function function, int index, String category) {
        List<Triple<String, String, String[]>> actions = function.getActions();
        List<Triple<String, String, String[]>> suites = function.getSuites();
        int yieldIndexActions = getYieldIndex(actions, index, category);
        checkYieldIndex(actions.size(), yieldIndexActions, index, category);
        int yieldIndexSuites = getYieldIndex(suites, index, category);
        checkYieldIndex(actions.size(), yieldIndexSuites, index, category);
        return new int[]{yieldIndexActions, yieldIndexSuites};
    }

    private int getYieldIndex(List<Triple<String, String, String[]>> pairs, int index, String category) {
        int yieldIndex = -1;
        int count = 0;
        for (int i = 0; i < pairs.size(); i++) {
            if (pairs.get(i).getFirst().equalsIgnoreCase(YIELD)) {
                yieldIndex = i;
                count++;
            }
        }
        if (count > 1) {
            String e = "one function only support one yield in category[" + category + "] in row [" + index + "]";
            throw new RuntimeException(e);
        }
        return yieldIndex;
    }

    /**
     * 判断是否有text描述，必须有
     *
     * @param pairs 类表
     */
    private void checkSuitePairs(List<Triple<String, String, String[]>> pairs, int yieldIndex) {
        /*
         * 检查YIELD的前后是否都有text，即text必须有两个，且text后面必须有一个
         */
        log.debug("pairs size is {} and yield Index is {}", pairs.size(), yieldIndex);
        int size = 5;
        if (pairs.size() < size) {
            String e = "not suitable for suite input, please check suite cell standard is:\n text\n operator\n yield\n text\n operator\n";
            throw new RuntimeException(e);
        }
        String firstText = pairs.get(0).getFirst();
        String firstTextAfter = pairs.get(1).getFirst();
        log.debug("firstText is {} and firstTextAfter is {}", firstText, firstTextAfter);
        if (!firstText.equalsIgnoreCase(TEXT)) {
            String e = "first line must be text";
            throw new RuntimeException(e);
        }
        if (firstTextAfter.equalsIgnoreCase(YIELD) || firstTextAfter.equalsIgnoreCase(TEXT)) {
            String e = "after first text must contain operator";
            throw new RuntimeException(e);
        }
        String lastText = pairs.get(yieldIndex + 1).getFirst();
        if (!lastText.equalsIgnoreCase(TEXT)) {
            String e = "after yield must be text";
            throw new RuntimeException(e);
        }
        try {
            String lastTextAfter = pairs.get(yieldIndex + 2).getFirst();
            if (lastTextAfter.equalsIgnoreCase(YIELD) || lastTextAfter.equalsIgnoreCase(TEXT)) {
                String e = "after first text must contain operator";
                throw new RuntimeException(e);
            }
        } catch (IndexOutOfBoundsException e) {
            String error = "after first text must contain operator";
            throw new RuntimeException(error);
        }
    }
}
