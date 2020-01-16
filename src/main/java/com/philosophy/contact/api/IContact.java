package com.philosophy.contact.api;


import java.util.List;

/**
 * @author lizhe
 */
public interface IContact {

    /**
     * 创建通讯录
     * @return 通讯录内容
     */
    List<String[]> generator();
}
