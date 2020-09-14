package com.chinatsp.code.checker.impl.storage;

import com.chinatsp.code.checker.api.BaseChecker;
import com.chinatsp.code.checker.api.IChecker;
import com.chinatsp.code.configure.Configure;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.dbc.entity.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class InformationChecker extends BaseChecker implements IChecker {


    @Override
    public void check(Map<String, List<BaseEntity>> map, List<Message> messages, Configure configure) {

    }
}
