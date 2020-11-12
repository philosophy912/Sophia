package com.chinatsp.automotive.writer.impl.compare;

import com.chinatsp.automotive.entity.BaseEntity;
import com.chinatsp.automotive.entity.compare.ElementCompare;
import com.chinatsp.automotive.enumeration.ElementCompareTypeEnum;
import com.chinatsp.automotive.writer.api.FreeMarker;
import com.chinatsp.automotive.writer.api.IFreeMarkerWriter;
import com.chinatsp.dbc.entity.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lizhe
 * @date 2020/9/30 13:55
 **/
@Component
public class ElementCompareWriter implements IFreeMarkerWriter {

    @Override
    public List<FreeMarker> convert(List<BaseEntity> entities, List<Message> messages) {
        String pre = this.getClass().getSimpleName().replace("Writer", "").toLowerCase() + "_";
        List<FreeMarker> freeMarkers = new ArrayList<>();
        for (BaseEntity baseEntity : entities) {
            FreeMarker freeMarker = new FreeMarker();
            ElementCompare elementCompare = (ElementCompare) baseEntity;
            Map<String, Object> map = new HashMap<>();
            map.put(FUNCTION_NAME, pre + elementCompare.getName());
            map.put(HANDLE_NAME, "android_service");
            map.put(HANDLE_FUNCTION, "exist");
            map.put(EXIST, elementCompare.getElementCompareType() == ElementCompareTypeEnum.NOT_EXIST ? "1" : "0");
            map.put(TIMEOUT, String.valueOf(elementCompare.getTimeout()));
            map.put(LOCATOR, elementCompare.getElement());
            freeMarker.setParams(map);
            freeMarker.setComment(elementCompare.getComments());
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
