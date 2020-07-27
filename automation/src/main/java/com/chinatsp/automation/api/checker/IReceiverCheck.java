package com.chinatsp.automation.api.checker;

import com.chinatsp.automation.entity.actions.CanAction;
import com.chinatsp.automation.entity.actions.ScreenAction;
import com.chinatsp.automation.entity.testcase.Receive;

import java.util.List;

/**
 * @author lizhe
 * @date 2020-05-27 21:33
 */
public interface IReceiverCheck {
    /**
     * 校验数据
     * @param receives  Receive
     * @param canActions CanAction
     * @param screenActions ScreenAction
     */
    void check(List<Receive> receives, List<CanAction> canActions, List<ScreenAction> screenActions);
}
