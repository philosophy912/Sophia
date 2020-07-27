package com.chinatsp.automation.entity.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lizhe
 * @date 2020/5/26 15:50
 **/
@Setter
@Getter
@ToString
public abstract class BaseEntity {
    /**
     * 序号
     */
    private Integer id;
    /**
     * 类别
     */
    private String category;

}
