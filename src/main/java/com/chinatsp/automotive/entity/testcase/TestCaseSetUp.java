package com.chinatsp.automotive.entity.testcase;

import com.chinatsp.automotive.entity.BaseEntity;
import com.chinatsp.automotive.enumeration.TestCaseFunctionTypeEnum;
import com.philosophy.base.common.Pair;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author lizhe
 * @date 2020/9/16 9:46
 **/
@Setter
@Getter
@ToString
public class TestCaseSetUp extends BaseEntity {
    /**
     * 测试用例函数执行前后
     */
    private List<Pair<TestCaseFunctionTypeEnum, String>> functions;
    /**
     * 函数执行前描述
     */
    private String functionsBefore;
    /**
     * 函数执行后描述
     */
    private String functionsAfter;
    /**
     * 测试套件执行前后
     */
    private List<Pair<TestCaseFunctionTypeEnum, String>> suites;
    /**
     * 套件执行前描述
     */
    private String suitesBefore;
    /**
     * 套件执行后描述
     */
    private String suitesAfter;

}
