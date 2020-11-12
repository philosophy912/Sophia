package com.chinatsp.automotive.writer.impl.action;

import com.chinatsp.automotive.entity.BaseEntity;
import com.chinatsp.automotive.entity.actions.CanAction;
import com.chinatsp.automotive.writer.api.FreeMarker;
import com.chinatsp.automotive.writer.api.IFreeMarkerWriter;
import com.chinatsp.dbc.entity.Message;
import com.chinatsp.dbc.entity.Signal;
import com.philosophy.base.common.Pair;
import com.philosophy.base.common.Triple;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CanActionWriter implements IFreeMarkerWriter {
    /**
     * 根据Signal的名字查找Message
     *
     * @param messages   can消息列表
     * @param signalName 信号名字
     * @return 消息对象
     */
    private Message getMessage(List<Message> messages, String signalName) {
        for (Message message : messages) {
            for (Signal signal : message.getSignals()) {
                if (signal.getName().equals(signalName)) {
                    return message;
                }
            }
        }
        throw new RuntimeException("can not found signalName in messages");
    }

    @Override
    public List<FreeMarker> convert(List<BaseEntity> entities, List<Message> messages) {
        String pre = this.getClass().getSimpleName().replace("Writer", "").toLowerCase() + "_";
        List<FreeMarker> freeMarkers = new ArrayList<>();
        for (BaseEntity entity : entities) {
            FreeMarker freeMarker = new FreeMarker();
            Map<String, Object> map = new HashMap<>();
            CanAction canAction = (CanAction) entity;
            map.put(FUNCTION_NAME, pre + canAction.getName());
            map.put(HANDLE_NAME, "can_service");
            map.put(HANDLE_FUNCTION, "send_can_signal_message");
            freeMarker.setParams(map);
            List<Triple<String, String, String>> triples = new ArrayList<>();
            List<Pair<String, String>> signals = canAction.getSignals();
            for (Pair<String, String> pair : signals) {
                String signalName = pair.getFirst();
                String value = pair.getSecond();
                String messageId = String.valueOf(getMessage(messages, signalName).getId());
                triples.add(new Triple<>(messageId, signalName, value));
            }
            freeMarker.setTriples(triples);
            freeMarker.setComment(canAction.getComments());
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
