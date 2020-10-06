package com.chinatsp.code.writer.api;

import com.philosophy.base.common.Pair;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class FreeMarker {

    private List<String> comment;

    private List<Pair<String, String>> pairs;

    private Map<String, String> params;

    private List<Pair<String, String>> swipeElement;

    private List<Pair<String, String>> locator;
}
