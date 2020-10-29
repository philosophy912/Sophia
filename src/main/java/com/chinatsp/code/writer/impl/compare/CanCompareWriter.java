package com.chinatsp.code.writer.impl.compare;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.compare.CanCompare;
import com.chinatsp.code.writer.api.FreeMarker;
import com.chinatsp.code.writer.api.IFreeMarkerWriter;
import com.chinatsp.dbc.entity.Message;
import com.chinatsp.dbc.entity.Signal;
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
        for (BaseEntity baseEntity : entities) {
            FreeMarker freeMarker = new FreeMarker();
            CanCompare canCompare = (CanCompare) baseEntity;
            Map<String, Object> map = new HashMap<>();
            String signalName = canCompare.getSignalName();
            map.put(FUNCTION_NAME, pre + canCompare.getName());
            map.put(HANDLE_NAME, "can_service");
            map.put(HANDLE_FUNCTION, "check_signal_value");
            map.put(SIGNAL_NAME, signalName);
            map.put(MESSAGE_ID, String.valueOf(getMessage(messages, signalName).getId()));
            map.put(EXPECT_VALUE, String.valueOf(canCompare.getExpectValue()));
            map.put(COUNT, String.valueOf(canCompare.getAppearCount()));
            map.put(EXACT, canCompare.getExact() ? "True" : "False");
            freeMarker.setParams(map);
            freeMarker.setComment(canCompare.getComments());
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
