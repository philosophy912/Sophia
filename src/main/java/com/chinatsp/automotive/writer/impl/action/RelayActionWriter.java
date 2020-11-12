package com.chinatsp.automotive.writer.impl.action;

import com.chinatsp.automotive.entity.BaseEntity;
import com.chinatsp.automotive.entity.actions.RelayAction;
import com.chinatsp.automotive.writer.api.FreeMarker;
import com.chinatsp.automotive.writer.api.IFreeMarkerWriter;
import com.chinatsp.dbc.entity.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RelayActionWriter implements IFreeMarkerWriter {
    @Override
    public List<FreeMarker> convert(List<BaseEntity> entities, List<Message> messages) {
        String pre = this.getClass().getSimpleName().replace("Writer","").toLowerCase() +"_";
        List<FreeMarker> freeMarkers = new ArrayList<>();
        for (BaseEntity entity : entities) {
            FreeMarker freeMarker = new FreeMarker();
            RelayAction relayAction = (RelayAction) entity;
            Map<String, Object> map = new HashMap<>();
            map.put(FUNCTION_NAME, pre + relayAction.getName());
            map.put(HANDLE_NAME, "relay");
            map.put(HANDLE_FUNCTION, relayAction.getRelayOperationType().getName());
            map.put(CHANNEL_INDEX, String.valueOf(relayAction.getChannelIndex()));
            freeMarker.setParams(map);
            freeMarker.setComment(relayAction.getComments());
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
