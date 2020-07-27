package com.chinatsp.automation.entity.actions;

import com.chinatsp.automation.entity.base.FunctionEntity;
import com.philosophy.base.common.Pair;
import com.philosophy.base.common.Triple;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author lizhe
 * @date 2020/5/26 16:06
 **/
@Setter
@Getter
@ToString
public class CanAction extends FunctionEntity {
    /**
     * 分类，只支持Condition和Action
     */
    private String category;
    /**
     * 信号列表，对应Excel表格的每一行
     */
    private List<Pair<String, String>> signals;
    /**
     * 依赖，仅适用于Action可能依赖Condition的情况
     */
    private List<Pair<String, String>> dependency;
    /**
     * 说明
     */
    private String description;
    /**
     * 是否需要去掉
     */
    private Long messageId = 0L;
}
