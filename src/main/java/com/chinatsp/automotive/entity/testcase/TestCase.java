package com.chinatsp.automotive.entity.testcase;

import com.chinatsp.automotive.entity.BaseEntity;
import com.chinatsp.automotive.enumeration.TestCaseFunctionTypeEnum;
import com.chinatsp.automotive.enumeration.TestCaseTypeEnum;
import com.philosophy.base.common.Pair;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author lizhe
 * @date 2020/8/27 10:01
 **/
@Setter
@Getter
@ToString
public class TestCase extends BaseEntity {
    /**
     * 中文描述
     */
    private String description;
    /**
     * 测试类型，目前仅支持TestCaseTypeEnum描述的类型
     */
    private TestCaseTypeEnum testCaseType;
    /**
     * 模块名
     * 可以是安卓也可以是仪表的
     */
    private String moduleName;
    /**
     * 前置条件描述
     */
    private List<String> preConditionDescription;
    /**
     * 前置条件执行函数
     */
    private List<Pair<TestCaseFunctionTypeEnum, String>> preCondition;
    /**
     * 执行步骤描述
     */
    private List<String> stepsDescription;
    /**
     * 执行步骤执行函数
     */
    private List<Pair<TestCaseFunctionTypeEnum, String>> steps;
    /**
     * 期望结果描述
     */
    private List<String> expectDescription;
    /**
     * 期望结果执行函数
     */
    private  List<Pair<TestCaseFunctionTypeEnum, String>> expect;
}
