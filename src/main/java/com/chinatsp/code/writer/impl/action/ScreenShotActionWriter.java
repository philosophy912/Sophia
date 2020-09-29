package com.chinatsp.code.writer.impl.action;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.actions.ScreenShotAction;
import com.chinatsp.code.enumeration.ScreenShotTypeEnum;
import com.chinatsp.code.writer.api.FreeMarker;
import com.chinatsp.code.writer.api.IFreeMarkerWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class ScreenShotActionWriter implements IFreeMarkerWriter {
    @Override
    public List<FreeMarker> convert(List<BaseEntity> entities) {
        List<FreeMarker> freeMarkers = new ArrayList<>();
        for (BaseEntity entity : entities) {
            FreeMarker freeMarker = new FreeMarker();
            ScreenShotAction screenShotAction = (ScreenShotAction) entity;
            String functionName = screenShotAction.getName();
            List<String> comments = screenShotAction.getComments();
            int count = screenShotAction.getCount();
            int displayId = screenShotAction.getDisplayId();
            String imageName = screenShotAction.getImageName();
            ScreenShotTypeEnum type = screenShotAction.getScreenShotType();
            String handleName = type.getName();
            String handleFunction = "screen_shot";
            String[] info = new String[5];
            info[0] = functionName;
            if (type == ScreenShotTypeEnum.ANDROID_DISPLAY || type == ScreenShotTypeEnum.CLUSTER_DISPLAY) {
                handleName = handleName + ".adb";
            }
            info[1] = handleName;
            info[2] = handleFunction;
            info[3] = imageName;
            info[4] = String.valueOf(count);
            // 组合param
            List<String> params = new LinkedList<>();
            if (type == ScreenShotTypeEnum.QNX_DISPLAY) {
                params.add("image_name=image_name");
                params.add("count=" + count);
                params.add("interval_time=interval_time");
                params.add("display=" + displayId);
            } else {
                params.add("display_id=" + displayId);
                params.add("device_id=android_device_id");
            }
            freeMarker.setInfo(info);
            freeMarker.setComment(comments);
            freeMarker.setParams(params);
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
