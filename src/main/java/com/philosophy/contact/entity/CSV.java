package com.philosophy.contact.entity;

import com.philosophy.api.character.ECharacterType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CSV {
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
    private ECharacterType type;
    /**
     * 电话号码的前缀
     */
    private PhoneNumber entity;

}
