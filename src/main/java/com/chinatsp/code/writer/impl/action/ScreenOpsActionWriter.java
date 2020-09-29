package com.chinatsp.code.writer.impl.action;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.actions.ScreenOpsAction;
import com.chinatsp.code.enumeration.DeviceTpeEnum;
import com.chinatsp.code.enumeration.ScreenOperationTypeEnum;
import com.chinatsp.code.writer.api.FreeMarker;
import com.chinatsp.code.writer.api.IFreeMarkerWriter;
import com.philosophy.base.common.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScreenOpsActionWriter implements IFreeMarkerWriter {
    @Override
    public List<FreeMarker> convert(List<BaseEntity> entities) {
        List<FreeMarker> freeMarkers = new ArrayList<>();
        for (BaseEntity entity : entities) {
            FreeMarker freeMarker = new FreeMarker();
            ScreenOpsAction screenOpsAction = (ScreenOpsAction) entity;
            String functionName = screenOpsAction.getName();
            List<String> comments = screenOpsAction.getComments();
            DeviceTpeEnum deviceType = screenOpsAction.getDeviceType();
            // python操作句柄
            StringBuilder handleName = new StringBuilder(deviceType.getName());
            ScreenOperationTypeEnum type = screenOpsAction.getScreenOperationType();
            // 操作的函数
            String handleFunction = type.getName();
            // 屏幕序号
            int displayId = screenOpsAction.getScreenIndex();
            // 持续时间
            Double continueTimes = screenOpsAction.getContinueTimes();
            String[] info = new String[3];
            info[0] = functionName;
            if (deviceType == DeviceTpeEnum.ANDROID) {
                handleName.append(".adb");
            }
            info[1] = handleName.toString();
            info[2] = handleFunction;
            List<Pair<Integer, Integer>> points = screenOpsAction.getPoints();
            // 组合param
            List<String> params = new ArrayList<>();
            if (deviceType == DeviceTpeEnum.ANDROID) {
                params.add("display_id=" + displayId);
                params.add("device_id=android_device_id");
                Pair<Integer, Integer> pair = points.get(0);
                params.add("x=" + pair.getFirst());
                params.add("y=" + pair.getSecond());
            } else {
                params.add("display=" + displayId);
                if (type == ScreenOperationTypeEnum.CLICK) {
                    Pair<Integer, Integer> pair = points.get(0);
                    params.add("x=" + pair.getFirst());
                    params.add("y=" + pair.getSecond());
                } else if (type == ScreenOperationTypeEnum.SLIDE) {
                    Pair<Integer, Integer> pair1 = points.get(0);
                    Pair<Integer, Integer> pair2 = points.get(1);
                    params.add("start_x=" + pair1.getFirst());
                    params.add("end_x=" + pair1.getSecond());
                    params.add("start_y=" + pair2.getFirst());
                    params.add("end_y=" + pair2.getSecond());
                    params.add("continue_time=" + continueTimes);
                } else {
                    Pair<Integer, Integer> pair = points.get(0);
                    params.add("x=" + pair.getFirst());
                    params.add("y=" + pair.getSecond());
                    params.add("continue_time=" + continueTimes);
                }
            }
            freeMarker.setInfo(info);
            freeMarker.setComment(comments);
            freeMarker.setParams(params);
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
