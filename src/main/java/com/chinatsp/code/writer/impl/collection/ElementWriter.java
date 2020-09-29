package com.chinatsp.code.writer.impl.collection;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.collection.Element;
import com.chinatsp.code.enumeration.AndroidLocatorTypeEnum;
import com.chinatsp.code.writer.api.FreeMarker;
import com.chinatsp.code.writer.api.IFreeMarkerWriter;
import com.philosophy.base.common.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ElementWriter implements IFreeMarkerWriter {

    @Override
    public List<FreeMarker> convert(List<BaseEntity> entities) {
        List<FreeMarker> freeMarkers = new ArrayList<>();
        for (BaseEntity entity : entities) {
            FreeMarker freeMarker = new FreeMarker();
            Element element = (Element) entity;
            String functionName = element.getName();
            List<String> comment = element.getComments();
            String[] info = new String[2];
            info[0] = functionName;
            List<Pair<String, String>> pairs = new ArrayList<>();
            Map<AndroidLocatorTypeEnum, String> locators = element.getLocators();
            for (Map.Entry<AndroidLocatorTypeEnum, String> entry : locators.entrySet()) {
                String type = entry.getKey().getValue();
                String value = entry.getValue();
                pairs.add(new Pair<>(type, value));
            }
            freeMarker.setInfo(info);
            freeMarker.setComment(comment);
            freeMarker.setPairs(pairs);
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
