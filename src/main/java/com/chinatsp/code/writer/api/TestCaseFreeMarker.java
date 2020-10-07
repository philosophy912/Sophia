package com.chinatsp.code.writer.api;

import com.chinatsp.code.enumeration.TestCaseTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TestCaseFreeMarker {
    /**
     * 序号
     * 校验数据时候用于提示哪行存在错误
     */
    private Integer id;
    /**
     * 名字
     * 一般用于函数名
     */
    private String name;
    /**
     * 中文描述
     */
    private String description;
    /**
     * 备注
     */
    private List<String> comments;
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
    private List<String> preCondition;
    /**
     * 执行步骤描述
     */
    private List<String> stepsDescription;
    /**
     * 执行步骤执行函数
     */
    private List<String> steps;
    /**
     * 期望结果描述
     */
    private List<String> expectDescription;
    /**
     * 期望结果执行函数
     */
    private  List<String> expect;
}
