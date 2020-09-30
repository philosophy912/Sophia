package com.chinatsp.code.writer.impl.compare;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.compare.CanCompare;
import com.chinatsp.code.writer.api.FreeMarker;
import com.chinatsp.code.writer.api.IFreeMarkerWriter;
import com.chinatsp.dbc.entity.Param;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
            String functionName = canCompare.getName();
            List<String> comments = canCompare.getComments();
            // python操作句柄
            String handleName = "can_service";
            // 操作的函数
            String handleFunction = "check_signal_value";
            String sigName = canCompare.getSignalName();
            List<String> params = new ArrayList<>();
            String[] info = new String[9];
            info[0] = functionName;
            info[1] = handleName;
            info[2] = handleFunction;
            info[3] = sigName;
            params.add("stack=stack");
            params.add("msg_id=" + canCompare.getMessageId());
            params.add("expect_value=" + canCompare.getExpectValue());
            int appearCount = canCompare.getAppearCount();
            if (appearCount > 0) {
                params.add("count=" + canCompare.getAppearCount());
            }
            params.add("exact=" + (canCompare.getExact() ? "True" : "False"));
            freeMarker.setInfo(info);
            freeMarker.setComment(comments);
            freeMarker.setParams(params);
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
