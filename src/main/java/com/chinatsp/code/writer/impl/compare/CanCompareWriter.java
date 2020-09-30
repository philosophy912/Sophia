package com.chinatsp.code.writer.impl.compare;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.compare.CanCompare;
import com.chinatsp.code.writer.api.FreeMarker;
import com.chinatsp.code.writer.api.IFreeMarkerWriter;
import com.chinatsp.dbc.entity.Param;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lizhe
 * @date 2020/9/30 11:47
 **/
@Component
public class CanCompareWriter implements IFreeMarkerWriter {

    @Override
    public List<FreeMarker> convert(List<BaseEntity> entities) {
        List<FreeMarker> freeMarkers = new ArrayList<>();
        for (BaseEntity baseEntity : entities) {
            FreeMarker freeMarker = new FreeMarker();
            CanCompare canCompare = (CanCompare) baseEntity;
            List<String> comments = canCompare.getComments();
            freeMarker.setComment(comments);
            Map<String, String> map = new HashMap<>();
            map.put(FUNCTION_NAME, canCompare.getName());
            map.put(HANDLE_NAME, "can_service");
            map.put(HANDLE_FUNCTION, "check_signal_value");
            map.put(SIGNAL_NAME, canCompare.getSignalName());
            map.put(MESSAGE_ID, String.valueOf(canCompare.getMessageId()));
            map.put(EXPECT_VALUE, String.valueOf(canCompare.getExpectValue()));
            map.put(COUNT, String.valueOf(canCompare.getAppearCount()));
            map.put(EXACT, canCompare.getExact() ? "True" : "False");
            freeMarker.setParams(map);
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
