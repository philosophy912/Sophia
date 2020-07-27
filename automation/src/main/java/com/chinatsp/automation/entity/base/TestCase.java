package com.chinatsp.automation.entity.base;

import com.philosophy.base.common.Triple;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author lizhe
 * @date 2020/5/26 15:51
 **/
@Setter
@Getter
@ToString
public class TestCase extends FunctionEntity {
    /**
     * 前置条件文字说明
     */
    private List<String> preCondition;
    /**
     * 前置条件函数名
     * Pair<String, String>  类别/函数名
     */
    private List<Triple<String, String, String[]>> preConditionFunctions;
    /**
     * 执行步骤文字说明
     */
    private List<String> steps;
    /**
     * 执行步骤函数名
     * Pair<String, String>  类别/函数名
     */
    private List<Triple<String, String, String[]>> stepsFunctions;
    /**
     * 期望结果文字说明
     */
    private List<String> expect;
    /**
     * 测试用例简要说明
     */
    private String description;
}
