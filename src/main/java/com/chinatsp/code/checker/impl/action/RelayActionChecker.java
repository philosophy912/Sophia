package com.chinatsp.code.checker.impl.action;

import com.chinatsp.code.checker.api.BaseChecker;
import com.chinatsp.code.checker.api.IEntityChecker;
import com.chinatsp.code.configure.Configure;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.actions.RelayAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class RelayActionChecker extends BaseChecker implements IEntityChecker {


    @Override
    public void check(List<BaseEntity> entities, Configure configure) {
        Integer maxChannel = configure.getMaxRelayChannel();
        for (int i = 0; i < entities.size(); i++) {
            int index = i+1;
            RelayAction relayAction = (RelayAction) entities.get(i);
            String name = relayAction.getClass().getName();
            checkUtils.checkPythonFunction(relayAction.getName(), index, name);
            checkUtils.checkRelayChannel(relayAction.getChannelIndex(), index, name, maxChannel);
        }
    }


}
