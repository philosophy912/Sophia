package com.chinatsp.code.checker.api;

import com.chinatsp.code.configure.Configure;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.dbc.entity.Message;

import java.util.List;
import java.util.Map;

public interface IChecker {
    /**
     * 检查自身是否有错误
     *
     * @param map       表格读出来的内容集合
     * @param messages  消息列表
     * @param configure 表中读取出来的配置文件
     */
    void check(Map<String, List<BaseEntity>> map, List<Message> messages, Configure configure);
}
