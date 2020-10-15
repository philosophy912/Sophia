package com.chinatsp.code.service.api;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.enumeration.ConfigureTypeEnum;
import com.chinatsp.dbc.entity.Message;
import com.philosophy.base.common.Pair;
import com.philosophy.base.common.Triple;

import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;

/**
 * @author lizhe
 * @date 2020/9/15 13:33
 **/
public interface IReadService {
    /**
     * 读取Excel文档，并生成对象
     *
     * @param excel excel文档
     * @return 生成的对象
     */
    Triple<Map<String, List<BaseEntity>>, Map<ConfigureTypeEnum, String>, List<Message>> read(Path excel);
}
