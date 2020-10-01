package com.chinatsp.code.writer.impl.common;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.common.Common;
import com.chinatsp.code.writer.api.FreeMarker;
import com.chinatsp.code.writer.api.IFreeMarkerWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommonWriter implements IFreeMarkerWriter {
    @Override
    public List<FreeMarker> convert(List<BaseEntity> entities) {
        List<FreeMarker> freeMarkers = new ArrayList<>();
        for (BaseEntity baseEntity : entities) {
            Common common = (Common) baseEntity;
            FreeMarker freeMarker = new FreeMarker();
            Map<String, String> map = new HashMap<>();
            map.put(FUNCTION_NAME, common.getName());

            freeMarker.setParams(map);
            freeMarker.setComment(common.getComments());
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
