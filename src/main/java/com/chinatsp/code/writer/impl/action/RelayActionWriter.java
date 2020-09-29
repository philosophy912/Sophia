package com.chinatsp.code.writer.impl.action;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.actions.RelayAction;
import com.chinatsp.code.writer.api.FreeMarker;
import com.chinatsp.code.writer.api.IFreeMarkerWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RelayActionWriter implements IFreeMarkerWriter {
    @Override
    public List<FreeMarker> convert(List<BaseEntity> entities) {
        List<FreeMarker> freeMarkers = new ArrayList<>();
        for (BaseEntity entity : entities) {
            FreeMarker freeMarker = new FreeMarker();
            RelayAction relayAction = (RelayAction) entity;
            String functionName = relayAction.getName();
            List<String> comments = relayAction.getComments();
            // python操作句柄
            String handleName = "relay";
            // 操作的函数
            String handleFunction = relayAction.getRelayOperationType().getName();
            int channelIndex = relayAction.getChannelIndex();
            String[] info = new String[4];
            info[0] = functionName;
            info[1] = handleName;
            info[2] = handleFunction;
            info[3] = String.valueOf(channelIndex);
            freeMarker.setInfo(info);
            freeMarker.setComment(comments);
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
