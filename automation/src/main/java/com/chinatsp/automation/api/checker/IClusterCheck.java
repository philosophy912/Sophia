package com.chinatsp.automation.api.checker;

import com.chinatsp.automation.entity.actions.CanAction;
import com.chinatsp.automation.entity.testcase.Cluster;

import java.util.List;

/**
 * @author lizhe
 * @date 2020-05-27 21:34
 */
public interface IClusterCheck {
    /**
     * 校验数据
     * @param clusters Cluster
     * @param canActions CanAction
     */
    void check(List<Cluster> clusters, List<CanAction> canActions);
}
