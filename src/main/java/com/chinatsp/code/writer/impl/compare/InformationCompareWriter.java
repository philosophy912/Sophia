package com.chinatsp.code.writer.impl.compare;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.compare.InformationCompare;
import com.chinatsp.code.writer.api.FreeMarker;
import com.chinatsp.code.writer.api.IFreeMarkerWriter;
import com.chinatsp.dbc.entity.Message;
import com.philosophy.base.util.StringsUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InformationCompareWriter implements IFreeMarkerWriter {

    @Override
    public List<FreeMarker> convert(List<BaseEntity> entities, List<Message> messages) {
        List<FreeMarker> freeMarkers = new ArrayList<>();
        for (BaseEntity baseEntity : entities) {
            FreeMarker freeMarker = new FreeMarker();
            InformationCompare informationCompare = (InformationCompare) baseEntity;
            Map<String, String> map = new HashMap<>();
            map.put(FUNCTION_NAME, informationCompare.getName());
            map.put(HANDLE_NAME, "android_service");
            map.put(HANDLE_FUNCTION, "get_element_attribute");
            map.put(LOCATOR, informationCompare.getElement());
            map.put(ELEMENT_ATTRIBUTE, informationCompare.getElementAttribute().getValue());
            if (StringsUtils.isNotEmpty(informationCompare.getInfo())) {
                // 元素比较
                map.put(INFORMATION_COMPARE_TYPE, "info");
            } else {
                // 和之前保存的元素信息比较
                map.put(INFORMATION_COMPARE_TYPE, "save");
            }
            map.put(SAVED_INFORMATION, informationCompare.getSavedInformation());
            map.put(INFORMATION, informationCompare.getInfo());
            map.put(COMPARE_TYPE, informationCompare.getInformationCompareType().getValue());
            freeMarker.setParams(map);
            freeMarker.setComment(informationCompare.getComments());
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
