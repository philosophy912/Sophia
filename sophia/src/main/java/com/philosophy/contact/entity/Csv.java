package com.philosophy.contact.entity;

import com.philosophy.character.api.CharEnum;
import lombok.Getter;
import lombok.Setter;
/**
 * @author lizhe
 */
@Setter
@Getter
public class Csv {
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
     * CSV写的类型
     */
    private CharEnum type;
    /**
     * 电话号码的前缀
     */
    private PhoneNumber entity;

}
