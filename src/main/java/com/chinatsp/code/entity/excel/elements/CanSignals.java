package com.chinatsp.code.entity.excel.elements;

import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author lizhe
 * @date 2020/8/14 14:40
 **/
@Setter
@Getter
@ToString
public class CanSignals {
    /**
     * 序号
     */
    private Integer id;
    /**
     * CAN信号设置的值
     */
    private List<Pair<String, String>> signals;
    /**
     * 备注
     */
    private String comments;
}
