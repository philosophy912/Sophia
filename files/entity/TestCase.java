package com.chinatsp.code.entity;

import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author lizhe
 * @date 2020/8/12 14:14
 **/
@Setter
@Getter
@ToString
public class TestCase {
    /**
     * 序号
     */
    private Integer id;
    /**
     * 类型
     */
    private String type;
    /**
     * 模块名
     */
    private String module;
    /**
     * 测试用例描述
     */
    private String description;
    /**
     * 测试用例函数名
     */
    private String testcaseFunctionName;
    /**
     * 前置条件描述
     */
    private List<String> preCondition;
    /**
     * 前置条件函数名列表
     */
    private List<Pair<String, String>> preConditionFunctions;
    /**
     * 执行步骤描述
     */
    private List<String> steps;
    /**
     * 执行步骤函数名列表
     */
    private List<Pair<String, String>> stepsFunctions;
    /**
     * 期望结果描述
     */
    private List<String> expectResult;
    /**
     * 期望结果函数名列表
     */
    private List<Pair<String, String>> expectResultFunctions;


}
