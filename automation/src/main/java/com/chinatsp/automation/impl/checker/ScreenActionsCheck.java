package com.chinatsp.automation.impl.checker;

import com.chinatsp.automation.api.checker.BaseCheck;
import com.chinatsp.automation.api.checker.IScreenActionsCheck;
import com.chinatsp.automation.entity.actions.ScreenAction;
import com.philosophy.base.common.Pair;
import com.philosophy.base.util.StringsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.chinatsp.automation.api.IConstant.CLICK;
import static com.chinatsp.automation.api.IConstant.PRESS;
import static com.chinatsp.automation.api.IConstant.SLIDE;

/**
 * @author lizhe
 * @date 2020/5/28 9:40
 **/
@Slf4j
@Component
public class ScreenActionsCheck extends BaseCheck implements IScreenActionsCheck {

    private static final String SHEET_NAME = "Sheet【屏幕操作】中";


    /**
     * 检查持续时间是否符合要求
     *
     * @param index 序号
     * @param time  持续时间
     */
    private void checkContinueTime(int index, double time) {
        if (time < 0 || time > 3) {
            String e = getError(SHEET_NAME, index) + "持续时间超过了3秒";
            throw new RuntimeException(e);
        }
    }

    /**
     * 检查点是否在最大最小点之间
     *
     * @param index 序号
     * @param pair  位置点
     */
    private void checkPosition(int index, Pair<String, String> pair, int width, int height) {
        int x = Integer.parseInt(pair.getFirst());
        int y = Integer.parseInt(pair.getSecond());
        if (x < 0 || x > width) {
            String e = getError(SHEET_NAME, index) + "坐标点[" + x + "]超过了[" + width + "]";
            throw new RuntimeException(e);
        }
        if (y < 0 || y > height) {
            String e = getError(SHEET_NAME, index) + "坐标点[\" + y + \"]超过了[\" + height + \"]";
            throw new RuntimeException(e);
        }
    }

    @Override
    public void check(List<ScreenAction> screenActions, int width, int height) {
        // 检查condition和FunctionName不能重名
        checkDuplicateName(screenActions);
        screenActions.forEach(screenAction -> {
            int index = screenAction.getId();
            String type = screenAction.getCategory();
            log.debug("type = {}", type);
            List<Pair<String, String>> positions = screenAction.getPosition();
            String continueTime = screenAction.getContinueTime();
            log.debug("continueTime = {}", continueTime);
            switch (type.toLowerCase()) {
                case SLIDE:
                    // 只允许有两个坐标点
                    if (positions.size() < 2) {
                        String e = getError(SHEET_NAME, index) + "的滑动有问题，必须要有两个坐标点";
                        throw new RuntimeException(e);
                    }
                    if (StringsUtils.isEmpty(continueTime)) {
                        String e = getError(SHEET_NAME, index) + "的持续时间有问题，必须要有持续时间";
                        throw new RuntimeException(e);
                    }
                    // todo 后续加入校验
                    checkContinueTime(index, Double.parseDouble(continueTime));
                    positions.forEach(pair -> checkPosition(index, pair, width, height));
                    break;
                case CLICK:
                    if (positions.size() != 1) {
                        String e = getError(SHEET_NAME, index) + "的点击操作有问题，只能有一个坐标点";
                        throw new RuntimeException(e);
                    }
                    positions.forEach(pair -> checkPosition(index, pair, width, height));
                    break;
                case PRESS:
                    if (positions.size() != 1) {
                        String e = getError(SHEET_NAME, index) + "的长按操作有问题，只能有一个坐标点";
                        throw new RuntimeException(e);
                    }
                    // todo 后续加入校验
                    checkContinueTime(index, Double.parseDouble(continueTime));
                    positions.forEach(pair -> checkPosition(index, pair, width, height));
                    break;
                default:
                    String e = getError(SHEET_NAME, index) + "的操作类别[" + type + "]有问题，目前只支持SLIDE/CLICK/PRESS两种模式";
                    throw new RuntimeException(e);
            }
        });
    }
}
