package com.chinatsp.automation.api.checker;

import com.chinatsp.automation.entity.actions.CanAction;
import com.chinatsp.automation.entity.actions.ScreenAction;
import com.chinatsp.automation.entity.testcase.Send;
import com.chinatsp.dbc.entity.Message;

import java.util.List;

/**
 * @author lizhe
 * @date 2020-05-27 21:33
 */
public interface ISenderCheck {
    /**
     * 校验数据
     * @param sends Send
     * @param canActions CanAction
     * @param screenActions ScreenAction
     * @param messages Message
     */
    void check(List<Send> sends, List<CanAction> canActions, List<ScreenAction> screenActions, List<Message> messages);
}
