package com.chinatsp.code.writer.impl.action;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.actions.ElementAction;
import com.chinatsp.code.writer.api.FreeMarker;
import com.chinatsp.code.writer.api.IFreeMarkerWriter;
import com.chinatsp.dbc.entity.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ElementActionWriter implements IFreeMarkerWriter {
    @Override
    public List<FreeMarker> convert(List<BaseEntity> entities, List<Message> messages) {
        List<FreeMarker> freeMarkers = new ArrayList<>();
        for (BaseEntity entity : entities) {
            FreeMarker freeMarker = new FreeMarker();
            ElementAction elementAction = (ElementAction) entity;
            Map<String, Object> map = new HashMap<>();
            map.put(FUNCTION_NAME, elementAction.getName());
            map.put(HANDLE_NAME, "android_service");
            map.put(HANDLE_FUNCTION, elementAction.getOperationActionType().getName());
            map.put(SLIDE_TIMES, String.valueOf(elementAction.getSlideTimes()));
            List<String> elements = elementAction.getElements();
            for (int i = 0; i < elements.size(); i++) {
                map.put(ELEMENT + (i + 1), elements.get(i));
            }
            freeMarker.setParams(map);
            freeMarker.setComment(elementAction.getComments());
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
