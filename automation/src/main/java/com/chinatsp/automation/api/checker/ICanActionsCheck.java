package com.chinatsp.automation.api.checker;

import com.chinatsp.automation.entity.actions.CanAction;
import com.chinatsp.dbc.entity.Message;

import java.util.List;

/**
 * @author lizhe
 * @date 2020-05-27 21:34
 */
public interface ICanActionsCheck {
    /**
     * 检查CanActions
     * @param canActionsList CanActions
     * @param messages messages
     */
    void check(List<CanAction> canActionsList, List<Message> messages);
}
