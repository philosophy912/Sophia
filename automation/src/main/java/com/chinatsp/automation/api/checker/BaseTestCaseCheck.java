package com.chinatsp.automation.api.checker;

import com.chinatsp.automation.entity.actions.CanAction;
import com.chinatsp.automation.entity.actions.ScreenAction;
import com.chinatsp.automation.entity.base.TestCase;
import com.chinatsp.automation.entity.testcase.Receive;
import com.chinatsp.automation.entity.testcase.Send;
import com.chinatsp.automation.entity.testcase.Cluster;
import com.philosophy.base.common.Pair;
import com.philosophy.base.common.Triple;
import com.philosophy.base.util.StringsUtils;

import java.util.List;

import static com.chinatsp.automation.api.IConstant.BLINK;
import static com.chinatsp.automation.api.IConstant.COMMENT_CHINESE;
import static com.chinatsp.automation.api.IConstant.DARK;
import static com.chinatsp.automation.api.IConstant.EXPECT_RESULT_CHINESE;
import static com.chinatsp.automation.api.IConstant.LIGHT;
import static com.chinatsp.automation.api.IConstant.PRECONDITION_CHINESE;
import static com.chinatsp.automation.api.IConstant.STEPS_CHINESE;

/**
 * @author lizhe
 * @date 2020/5/28 11:19
 **/
public abstract class BaseTestCaseCheck extends BaseCheck {

    /**
     * 检查期望结果
     *
     * @param pairs 期望结果每个cell
     * @param index 序号
     */
    protected void checkExcept(List<Triple<String, String, String[]>> pairs, int index, String sheetName) {
        if (pairs.size() != 1) {
            String e = getError(sheetName, index) + "期望结果只允许有一个，请检查";
            throw new RuntimeException(e);
        }
        for (Pair<String, String> pair : pairs) {
            String type = pair.getFirst();
            if (!(DARK.equalsIgnoreCase(type) || LIGHT.equalsIgnoreCase(type) || BLINK.equalsIgnoreCase(type))) {
                String e = getError(sheetName, index) + type + "不正确，只支持DARK和LIGHT和BLANK，请检查";
                throw new RuntimeException(e);
            }
        }
    }

    protected <T> void checkObject(Object object, Integer index, String sheetName, List<CanAction> canActions, List<ScreenAction> screenActions) {
        Class<?> clazz = object.getClass();
        if (clazz == Receive.class || clazz == Send.class || clazz == Cluster.class) {
            TestCase entity = (TestCase) object;
            // 检查前置条件描述
            checkText(entity.getPreCondition(), index, sheetName, PRECONDITION_CHINESE);
            // 检查前置条件
            checkPairs(entity.getPreConditionFunctions(), index, sheetName, canActions, screenActions);
            // 检查步骤描述
            checkText(entity.getSteps(), index, sheetName, STEPS_CHINESE);
            // 检查步骤
            checkPairs(entity.getStepsFunctions(), index, sheetName, canActions, screenActions);
            // 检查期望结果描述
            checkText(entity.getExpect(), index, sheetName, EXPECT_RESULT_CHINESE);
            // 检查备注信息
            if (StringsUtils.isEmpty(entity.getDescription())) {
                String e = getError(sheetName, index) + "的" + COMMENT_CHINESE + "未填写，请检查";
                throw new RuntimeException(e);
            }
        }
    }


}
