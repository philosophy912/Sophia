package com.chinatsp.code.checker.impl.compare;

import com.chinatsp.code.checker.api.BaseChecker;
import com.chinatsp.code.checker.api.IMessageEntityChecker;
import com.chinatsp.code.configure.Configure;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.dbc.entity.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CanCompareChecker extends BaseChecker implements IMessageEntityChecker {




    @Override
    public void check(List<BaseEntity> entities, List<Message> messages, Configure configure) {

    }
}
