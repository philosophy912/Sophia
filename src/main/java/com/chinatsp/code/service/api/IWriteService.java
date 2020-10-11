package com.chinatsp.code.service.api;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.enumeration.ConfigureTypeEnum;
import com.chinatsp.dbc.entity.Message;
import com.philosophy.base.common.Pair;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface IWriteService {
    /**
     * 写入用例到python文件中
     * @param pair excel中读取出来的内容
     * @param folder 要写入的根路径（自行创建路径)
     */
    void write(Pair<Map<String, List<BaseEntity>>, Map<ConfigureTypeEnum, String>> pair, List<Message> messages, Path folder);
}
