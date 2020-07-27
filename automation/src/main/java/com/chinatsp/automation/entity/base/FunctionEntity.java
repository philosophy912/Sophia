package com.chinatsp.automation.entity.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lizhe
 * @date 2020/6/11 11:58
 **/
@Setter
@Getter
@ToString
public abstract class FunctionEntity extends BaseEntity {
    /**
     * 函数名
     */
    private String functionName;

}
