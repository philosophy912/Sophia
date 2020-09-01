package com.chinatsp.code.entity.collection;

import com.chinatsp.code.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @author lizhe
 * @date 2020/8/27 12:15
 **/
@Setter
@Getter
@ToString
public class Can extends BaseEntity {
    /**
     * CAN信号名和值，允许有多个
     */
    private List<Map<String, String>> signals;
}
