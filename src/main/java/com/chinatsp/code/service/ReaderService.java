package com.chinatsp.code.service;

import com.chinatsp.code.checker.Checker;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.enumeration.ConfigureTypeEnum;
import com.chinatsp.code.reader.Reader;
import com.chinatsp.code.service.api.IReadService;
import com.chinatsp.dbc.entity.Message;
import com.philosophy.base.common.Pair;
import com.philosophy.base.common.Triple;
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
    public Triple<Map<String, List<BaseEntity>>, Map<ConfigureTypeEnum, String>, List<Message>> read(Path path) {
        Pair<Map<String, List<BaseEntity>>, Map<ConfigureTypeEnum, String>> pair = reader.readTestCase(path);
        List<Message> messages = checker.check(pair.getFirst(), pair.getSecond(), path.getParent().toAbsolutePath().toString());
        return new Triple<>(pair.getFirst(), pair.getSecond(), messages);
    }
}
