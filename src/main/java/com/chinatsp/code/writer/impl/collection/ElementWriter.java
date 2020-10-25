package com.chinatsp.code.writer.impl.collection;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.collection.Element;
import com.chinatsp.code.enumeration.AndroidLocatorTypeEnum;
import com.chinatsp.code.writer.api.FreeMarker;
import com.chinatsp.code.writer.api.IFreeMarkerWriter;
import com.chinatsp.dbc.entity.Message;
import com.philosophy.base.common.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ElementWriter implements IFreeMarkerWriter {

    @Override
    public List<FreeMarker> convert(List<BaseEntity> entities, List<Message> messages) {
        List<FreeMarker> freeMarkers = new ArrayList<>();
        for (BaseEntity entity : entities) {
            FreeMarker freeMarker = new FreeMarker();
            Element element = (Element) entity;
            Map<String, Object> map = new HashMap<>();
            map.put(FUNCTION_NAME, element.getName());
            List<Pair<String, String>> pairs = new ArrayList<>();
            Map<AndroidLocatorTypeEnum, String> locators = element.getLocators();
            for (Map.Entry<AndroidLocatorTypeEnum, String> entry : locators.entrySet()) {
                String type = entry.getKey().getValue();
                String value = entry.getValue();
                pairs.add(new Pair<>(type, value));
            }
            freeMarker.setParams(map);
            freeMarker.setPairs(pairs);
            freeMarker.setComment(element.getComments());
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
