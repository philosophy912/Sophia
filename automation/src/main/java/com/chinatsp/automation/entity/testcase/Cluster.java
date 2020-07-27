package com.chinatsp.automation.entity.testcase;

import com.chinatsp.automation.entity.base.TestCase;
import com.chinatsp.automation.entity.compare.Compare;
import com.philosophy.base.common.Pair;
import com.philosophy.base.common.Triple;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 仪表测试用例，意味着带图像对比
 * @author lizhe
 * @date 2020/5/26 15:59
 **/
@Setter
@Getter
@ToString
public class Cluster extends TestCase {
    /**
     * 期望结果函数名， 类别可能为空
     * Pair<String, String> 类别/函数名
     */
    private List<Triple<String, String, String[]>> expectFunctions;
    /**
     * 读取配置参数
     */
    private Compare compare;
}
