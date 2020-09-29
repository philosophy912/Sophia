package com.chinatsp.code.writer.api;

import com.philosophy.base.common.Pair;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class FreeMarker {

    private String[] info;

    private List<String> comment;

    private List<Pair<String, String>> pairs;

    private List<String> params;
}
