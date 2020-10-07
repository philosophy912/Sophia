package com.chinatsp.code.writer.api;

import com.philosophy.base.common.Pair;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class TestCaseFreeMarkers {
    /**
     * 模块名
     */
    private String moduleName;
    /**
     * 测试用例部分
     */
    private List<TestCaseFreeMarker> testcases;
    /**
     * suite部分， 其中Pair<List<String>, List<String>>存放suite前半部分和后半部分
     * Pair<String, String>是存放执行的步骤描述的前半部分和后半部分
     */
    private Pair<Pair<List<String>, List<String>>, Pair<String, String>> suite;
    /**
     * function部分， 其中Pair<List<String>, List<String>>存放function前半部分和后半部分
     * Pair<String, String>是存放执行的步骤描述的前半部分和后半部分
     */
    private Pair<Pair<List<String>, List<String>>, Pair<String, String>> function;

}
