package com.chinatsp.code.writer.impl.storage;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.storage.Information;
import com.chinatsp.code.writer.api.FreeMarker;
import com.chinatsp.code.writer.api.IFreeMarkerWriter;
import com.chinatsp.dbc.entity.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InformationWriter implements IFreeMarkerWriter {

    @Override
    public List<FreeMarker> convert(List<BaseEntity> entities, List<Message> messages) {
        List<FreeMarker> freeMarkers = new ArrayList<>();
        for(BaseEntity baseEntity: entities){
            FreeMarker freeMarker = new FreeMarker();
            Information information = (Information) baseEntity;
            Map<String, String> map = new HashMap<>();
            map.put(FUNCTION_NAME, information.getName());
            map.put(HANDLE_NAME, "android_service");
            map.put(HANDLE_FUNCTION, "get_element_attribute");
            map.put(ELEMENT_ATTRIBUTE, information.getElementAttribute().getValue());
            map.put(LOCATOR, information.getElement());
            freeMarker.setParams(map);
            freeMarker.setComment(information.getComments());
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
