package com.chinatsp.code.entity.testcase;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.enumeration.TestCaseTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lizhe
 * @date 2020/8/27 10:01
 **/
@Setter
@Getter
@ToString
public class TestCase extends BaseEntity {
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
    private String preConditionDescription;
    /**
     * 执行步骤描述
     */
    private String stepsDescription;
    /**
     * 期望结果描述
     */
    private String expectDescription;
}
