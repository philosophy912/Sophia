package com.chinatsp.code.writer.impl.action;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.actions.ScreenOpsAction;
import com.chinatsp.code.enumeration.DeviceTpeEnum;
import com.chinatsp.code.enumeration.ScreenOperationTypeEnum;
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
public class ScreenOpsActionWriter implements IFreeMarkerWriter {
    @Override
    public List<FreeMarker> convert(List<BaseEntity> entities, List<Message> messages) {
        List<FreeMarker> freeMarkers = new ArrayList<>();
        for (BaseEntity entity : entities) {
            FreeMarker freeMarker = new FreeMarker();
            ScreenOpsAction screenOpsAction = (ScreenOpsAction) entity;
            DeviceTpeEnum deviceType = screenOpsAction.getDeviceType();
            ScreenOperationTypeEnum type = screenOpsAction.getScreenOperationType();
            Map<String, Object> map = new HashMap<>();
            map.put(FUNCTION_NAME, screenOpsAction.getName());
            if(deviceType == DeviceTpeEnum.ANDROID){
                map.put(HANDLE_NAME, deviceType.getName() + ".adb");
            }else{
                map.put(HANDLE_NAME, deviceType.getName());
            }
            map.put(HANDLE_FUNCTION, type.getName());
            map.put(DISPLAY_ID, String.valueOf(screenOpsAction.getScreenIndex()));
            map.put(CONTINUE_TIMES, String.valueOf(screenOpsAction.getContinueTimes()));
            List<Pair<Integer, Integer>> points = screenOpsAction.getPoints();
            if (type == ScreenOperationTypeEnum.SLIDE){
                Pair<Integer, Integer> pair1 = points.get(0);
                Pair<Integer, Integer> pair2 = points.get(1);
                map.put(START_X, String.valueOf(pair1.getFirst()));
                map.put(END_X, String.valueOf(pair1.getSecond()));
                map.put(START_Y, String.valueOf(pair2.getFirst()));
                map.put(END_Y, String.valueOf(pair2.getSecond()));
            }else{
                Pair<Integer, Integer> pair = points.get(0);
                map.put(X, String.valueOf(pair.getFirst()));
                map.put(Y, String.valueOf(pair.getSecond()));
            }
            freeMarker.setParams(map);
            freeMarker.setComment(screenOpsAction.getComments());
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
