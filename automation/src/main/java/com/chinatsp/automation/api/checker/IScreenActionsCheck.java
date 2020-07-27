package com.chinatsp.automation.api.checker;

import com.chinatsp.automation.entity.actions.ScreenAction;

import java.util.List;

/**
 * @author lizhe
 * @date 2020-05-27 21:35
 */
public interface IScreenActionsCheck {
    /**
     * 校验数据
     * @param screenActions ScreenAction
     * @param width 高
     * @param height 宽
     */
    void check(List<ScreenAction> screenActions, int width, int height);
}
