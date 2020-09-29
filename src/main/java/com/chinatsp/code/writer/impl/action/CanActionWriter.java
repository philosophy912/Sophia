package com.chinatsp.code.writer.impl.action;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.actions.CanAction;
import com.chinatsp.code.writer.api.FreeMarker;
import com.chinatsp.code.writer.api.IFreeMarkerWriter;
import com.philosophy.base.common.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CanActionWriter implements IFreeMarkerWriter {
    @Override
    public List<FreeMarker> convert(List<BaseEntity> entities) {
        List<FreeMarker> freeMarkers = new ArrayList<>();
        for (BaseEntity entity : entities) {
            FreeMarker freeMarker = new FreeMarker();
            CanAction canAction = (CanAction) entity;
            String functionName = canAction.getName();
            List<String> comments = canAction.getComments();
            // python操作句柄
            String handleName = "can_service";
            // 操作的函数
            String handleFunction = "send_can_signal_message";
            Long msgId = canAction.getMessageId();
            String[] info = new String[4];
            info[0] = functionName;
            info[1] = handleName;
            info[2] = handleFunction;
            info[3] = String.valueOf(msgId);
            List<Pair<String, String>> signals = canAction.getSignals();
            freeMarker.setInfo(info);
            freeMarker.setComment(comments);
            freeMarker.setPairs(signals);
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
