package com.chinatsp.code.writer.impl.action;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.actions.RelayAction;
import com.chinatsp.code.writer.api.FreeMarker;
import com.chinatsp.code.writer.api.IFreeMarkerWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RelayActionWriter implements IFreeMarkerWriter {
    @Override
    public List<FreeMarker> convert(List<BaseEntity> entities) {
        List<FreeMarker> freeMarkers = new ArrayList<>();
        for (BaseEntity entity : entities) {
            FreeMarker freeMarker = new FreeMarker();
            RelayAction relayAction = (RelayAction) entity;
            List<String> comments = relayAction.getComments();
            freeMarker.setComment(comments);
            Map<String, String> map = new HashMap<>();
            map.put(FUNCTION_NAME, relayAction.getName());
            map.put(HANDLE_NAME, "relay");
            map.put(HANDLE_FUNCTION, relayAction.getRelayOperationType().getName());
            map.put(CHANNEL_INDEX, String.valueOf(relayAction.getChannelIndex()));
            freeMarker.setParams(map);
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
