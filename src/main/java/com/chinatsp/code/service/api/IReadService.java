package com.chinatsp.code.service.api;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.enumeration.ConfigureTypeEnum;
import com.philosophy.base.common.Pair;

import java.nio.file.Path;
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
    Pair<Map<String, List<BaseEntity>>, Map<ConfigureTypeEnum, String>> read(Path excel);
}
