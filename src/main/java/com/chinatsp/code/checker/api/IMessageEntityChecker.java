package com.chinatsp.code.checker.api;

import com.chinatsp.code.configure.Configure;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.dbc.entity.Message;

import java.util.List;

public interface IMessageEntityChecker {
    /**
     * 检查自身是否有错误
     *
     * @param entities 表格读出来的内容
     * @param messages 消息列表
     */
    void check(List<BaseEntity> entities, List<Message> messages, Configure configure);
}
