package com.chinatsp.code.service.api;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.enumeration.ConfigureTypeEnum;
import com.philosophy.base.common.Pair;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface IWriteService {

    void write(Pair<Map<String, List<BaseEntity>>, Map<ConfigureTypeEnum, String>> pair, Path folder);
}
