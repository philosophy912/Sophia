package com.chinatsp.automotive.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author lizhe
 * @date 2020/8/27 10:00
 **/
@Setter
@Getter
@ToString
public abstract class BaseEntity {
    /**
     * 序号
     * 校验数据时候用于提示哪行存在错误
     */
    private Integer id;
    /**
     * 名字
     * 一般用于函数名
     */
    private String name;
    /**
     * 备注
     */
    private List<String> comments;
}
