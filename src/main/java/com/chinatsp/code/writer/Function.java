package com.chinatsp.code.writer;

import com.philosophy.base.common.Pair;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
public class Function {

    private String name;
    private List<String> comments;
    private List<Pair<String, String>> pairs;
}
