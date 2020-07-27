package com.chinatsp.automation.api.checker;

import com.chinatsp.automation.entity.actions.CanAction;
import com.chinatsp.automation.entity.actions.ScreenAction;
import com.chinatsp.automation.entity.compare.Function;

import java.util.List;

/**
 * @author lizhe
 * @date 2020/6/11 11:15
 **/
public interface IFunctionCheck {

    void check(List<Function> functions, List<CanAction> canActions, List<ScreenAction> screenActions);
}
