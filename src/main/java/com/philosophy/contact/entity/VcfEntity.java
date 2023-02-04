package com.philosophy.contact.entity;

import lombok.Getter;
import lombok.Setter;
/**
 * @author lizhe
 */
@Setter
@Getter
public class VcfEntity extends ContactName {
    /**
     * 姓的相关Bean
     */
    private ContactName first;
    /**
     * 名的相关Bean
     */
    private ContactName last;
    /**
     * 通讯录数量
     */
    private int contactSize;
    /**
     * 电话号码的前缀
     */
    private PhoneNumber entity;
}
