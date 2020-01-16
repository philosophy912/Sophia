package com.philosophy.contact.common;

import com.philosophy.character.api.CharEnum;
import com.philosophy.character.api.ICharFactory;
import com.philosophy.character.factory.MixCharFactory;
import com.philosophy.character.factory.SingleCharFactory;
import com.philosophy.character.util.CharUtils;
import com.philosophy.contact.entity.ContactName;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhe
 * @since V1.0.0 2019/7/28 21:35
 **/
@Slf4j
class Contact {
    /**
     * 生成通讯录的名字
     *
     * @param contactName 通讯录名字对象
     * @param i           第几个 （用于生成数字前缀）
     * @param contactSize 通讯录数量 （用于生成数字前缀）
     * @return 字符串
     */
    public String genName(ContactName contactName, int i, int contactSize) {
        boolean isExt = contactName.isExt();
        int type = contactName.getType();
        int length = contactName.getLength();
        String result;
        if (isExt) {
            result = CharUtils.setPrefixNumber(i, contactSize) + createCharacter(CharEnum.fromValue(type), length);
        } else {
            result = createCharacter(CharEnum.fromValue(type), length);
        }
        log.debug("GenName is " + result);
        return result;
    }

    /**
     * 创建字符串
     *
     * @param type 类型
     * @param size 长度
     * @return 字符串
     */
    private String createCharacter(CharEnum type, int size) {
        ICharFactory factory;
        if (type == CharEnum.CHINESE || type == CharEnum.ENGLISH
                || type == CharEnum.SYMBOL || type == CharEnum.NUMBER) {
            factory = new SingleCharFactory();
        } else {
            factory = new MixCharFactory();
        }
        return factory.create(type, size);
    }

    /**
     * 用于生成数字字符串数组
     *
     * @param preNumber 前缀数字
     * @param size      数组大小
     * @return 字符串数组（数字）
     */
    public String[] genNumberSize(String preNumber, int size) {
        int numberSize = 11;
        if (!"".equals(preNumber)) {
            numberSize = 11 - preNumber.length();
        }
        List<String> numbers = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            numbers.add(createCharacter(CharEnum.NUMBER, numberSize));
        }
        int arraySize = numbers.size();
        return numbers.toArray(new String[arraySize]);
    }
}
