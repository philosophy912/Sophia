package com.chinatsp.code.writer.impl.action;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.actions.ScreenShotAction;
import com.chinatsp.code.enumeration.ScreenShotTypeEnum;
import com.chinatsp.code.writer.api.FreeMarker;
import com.chinatsp.code.writer.api.IFreeMarkerWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ScreenShotActionWriter implements IFreeMarkerWriter {
    @Override
    public List<FreeMarker> convert(List<BaseEntity> entities) {
        List<FreeMarker> freeMarkers = new ArrayList<>();
        for (BaseEntity entity : entities) {
            FreeMarker freeMarker = new FreeMarker();
            ScreenShotAction screenShotAction = (ScreenShotAction) entity;
            ScreenShotTypeEnum type = screenShotAction.getScreenShotType();
            Map<String, String> map = new HashMap<>();
            map.put(FUNCTION_NAME, screenShotAction.getName());
            if (type == ScreenShotTypeEnum.ANDROID_DISPLAY || type == ScreenShotTypeEnum.CLUSTER_DISPLAY) {
                map.put(HANDLE_NAME, type.getName() + ".adb");
            }else{
                map.put(HANDLE_NAME, type.getName());
            }
            map.put(HANDLE_FUNCTION, "screen_shot");
            map.put(IMAGE_NAME, screenShotAction.getImageName());
            map.put(DISPLAY_ID, String.valueOf(screenShotAction.getDisplayId()));
            map.put(COUNT, String.valueOf(screenShotAction.getCount()));
            freeMarker.setParams(map);
            freeMarker.setComment(screenShotAction.getComments());
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
