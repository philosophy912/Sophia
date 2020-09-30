package com.chinatsp.code.writer.impl.action;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.actions.CanAction;
import com.chinatsp.code.writer.api.FreeMarker;
import com.chinatsp.code.writer.api.IFreeMarkerWriter;
import com.philosophy.base.common.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CanActionWriter implements IFreeMarkerWriter {
    @Override
    public List<FreeMarker> convert(List<BaseEntity> entities) {
        List<FreeMarker> freeMarkers = new ArrayList<>();
        for (BaseEntity entity : entities) {
            FreeMarker freeMarker = new FreeMarker();
            Map<String, String> map = new HashMap<>();
            CanAction canAction = (CanAction) entity;
            List<String> comments = canAction.getComments();
            freeMarker.setComment(comments);
            map.put(FUNCTION_NAME, canAction.getName());
            map.put(HANDLE_NAME, "can_service");
            map.put(HANDLE_FUNCTION, "send_can_signal_message");
            map.put(MESSAGE_ID, String.valueOf(canAction.getMessageId()));
            freeMarker.setParams(map);
            List<Pair<String, String>> signals = canAction.getSignals();
            freeMarker.setPairs(signals);
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
