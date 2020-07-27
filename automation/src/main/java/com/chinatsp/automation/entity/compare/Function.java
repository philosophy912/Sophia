package com.chinatsp.automation.entity.compare;

import com.chinatsp.automation.entity.base.BaseEntity;
import com.philosophy.base.common.Pair;
import com.philosophy.base.common.Triple;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author lizhe
 * @date 2020/6/11 11:04
 **/
@Setter
@Getter
@ToString
public class Function extends BaseEntity {
    /**
     * 函数执行前
     */
    private List<Triple<String, String, String[]>> actions;
    /**
     * 类执行前
     */
    private List<Triple<String, String, String[]>> suites;
}
