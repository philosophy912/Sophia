package com.chinatsp.code.checker.impl.collection;

import com.chinatsp.code.checker.api.BaseChecker;
import com.chinatsp.code.checker.api.IMessageEntityChecker;
import com.chinatsp.code.configure.Configure;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.dbc.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CanChecker extends BaseChecker implements IMessageEntityChecker {


    @Override
    public void check(List<BaseEntity> entities, List<Message> messages, Configure configure) {

    }
}
