package com.chinatsp.code.writer.impl.action;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.actions.ElementAction;
import com.chinatsp.code.writer.api.FreeMarker;
import com.chinatsp.code.writer.api.IFreeMarkerWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ElementActionWriter implements IFreeMarkerWriter {
    @Override
    public List<FreeMarker> convert(List<BaseEntity> entities) {
        List<FreeMarker> freeMarkers = new ArrayList<>();
        for (BaseEntity entity : entities) {
            FreeMarker freeMarker = new FreeMarker();
            ElementAction elementAction = (ElementAction) entity;
            String functionName = elementAction.getName();
            List<String> comments = elementAction.getComments();
            // python操作句柄
            String handleName = "android_service";
            // 操作的函数
            String handleFunction = elementAction.getOperationActionType().getName();
            int slideTimes = elementAction.getSlideTimes();
            List<String> params = elementAction.getElements();
            String[] info = new String[4];
            info[0] = functionName;
            info[1] = handleName;
            info[2] = handleFunction;
            info[3] = String.valueOf(slideTimes);
            freeMarker.setInfo(info);
            freeMarker.setComment(comments);
            freeMarker.setParams(params);
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
