package com.chinatsp.code.entity.testcase;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.enumeration.TestCaseFunctionTypeEnum;
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
     * 测试套件执行前后
     */
    private List<Pair<TestCaseFunctionTypeEnum, String>> suites;

}
