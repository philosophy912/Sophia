package com.chinatsp.automotive.writer.impl.action;

import com.chinatsp.automotive.entity.BaseEntity;
import com.chinatsp.automotive.entity.actions.ElementAction;
import com.chinatsp.automotive.writer.api.FreeMarker;
import com.chinatsp.automotive.writer.api.IFreeMarkerWriter;
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
        String pre = this.getClass().getSimpleName().replace("Writer", "").toLowerCase() + "_";
        List<FreeMarker> freeMarkers = new ArrayList<>();
        for (BaseEntity entity : entities) {
            FreeMarker freeMarker = new FreeMarker();
            ElementAction elementAction = (ElementAction) entity;
            Map<String, Object> map = new HashMap<>();
            map.put(FUNCTION_NAME, pre + elementAction.getName());
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
