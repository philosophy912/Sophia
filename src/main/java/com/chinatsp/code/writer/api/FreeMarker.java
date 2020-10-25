package com.chinatsp.code.writer.api;

import com.philosophy.base.common.Pair;
import com.philosophy.base.common.Triple;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class FreeMarker {

    private List<String> comment;

    private List<Triple<String, String, String>> triples;

    private List<Pair<String, String>> pairs;
    /**
     * 函数参数，需要适配字典类型、字符串类型（带引号解析）以及其他
     */
    private Map<String, Object> params;

    private List<Triple<String, Object, String>> paramList;
}
