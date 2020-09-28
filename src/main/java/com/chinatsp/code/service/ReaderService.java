package com.chinatsp.code.service;

import com.chinatsp.code.checker.Checker;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.enumeration.ConfigureTypeEnum;
import com.chinatsp.code.reader.Reader;
import com.chinatsp.code.service.api.IReadService;
import com.philosophy.base.common.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * @author lizhe
 * @date 2020/9/15 13:27
 **/
@Service
@Slf4j
public class ReaderService implements IReadService {
    @Resource
    private Reader reader;
    @Resource
    private Checker checker;


    @Override
    public Pair<Map<String, List<BaseEntity>>, Map<ConfigureTypeEnum, String>> read(Path path) {
        Pair<Map<String, List<BaseEntity>>, Map<ConfigureTypeEnum, String>> pair = reader.readTestCase(path);
        checker.check(pair.getFirst(), pair.getSecond());
        return pair;
    }
}
