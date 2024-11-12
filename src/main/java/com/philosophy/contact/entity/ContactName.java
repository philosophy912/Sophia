package com.philosophy.contact.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 */
@Setter
@Getter
public class ContactName {

    /**
     * 需要生成的通讯录名字类型
     */
    private int type;
    /**
     * 名字的长度
     */
    private int length;
    /**
     * 是否需要加前缀
     */
    private boolean isExt;


}
