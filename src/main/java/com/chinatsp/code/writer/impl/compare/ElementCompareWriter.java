package com.chinatsp.code.writer.impl.compare;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.compare.ElementCompare;
import com.chinatsp.code.enumeration.ElementCompareTypeEnum;
import com.chinatsp.code.writer.api.FreeMarker;
import com.chinatsp.code.writer.api.IFreeMarkerWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lizhe
 * @date 2020/9/30 13:55
 **/
@Component
public class ElementCompareWriter implements IFreeMarkerWriter {

    @Override
    public List<FreeMarker> convert(List<BaseEntity> entities) {
        List<FreeMarker> freeMarkers = new ArrayList<>();
        for (BaseEntity baseEntity : entities) {
            FreeMarker freeMarker = new FreeMarker();
            ElementCompare elementCompare = (ElementCompare) baseEntity;
            String functionName = elementCompare.getName();
            List<String> comments = elementCompare.getComments();
            // python操作句柄
            String handleName = "android_service";
            // 操作的函数
            String handleFunction = "exist";
            String[] info = new String[4];
            info[0] = functionName;
            info[1] = handleName;
            info[2] = handleFunction;
            info[3] = elementCompare.getElementCompareType() == ElementCompareTypeEnum.NOT_EXIST ? "1" : "0";
            double timeout = elementCompare.getTimeout();
            List<String> params = new LinkedList<>();
            params.add("locator=" + elementCompare.getElement());
            params.add("timeout=" + timeout);
            freeMarker.setInfo(info);
            freeMarker.setComment(comments);
            freeMarker.setParams(params);
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
