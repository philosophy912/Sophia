package com.chinatsp.code.writer.impl.compare;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.compare.ImageCompare;
import com.chinatsp.code.writer.api.FreeMarker;
import com.chinatsp.code.writer.api.IFreeMarkerWriter;
import com.chinatsp.dbc.entity.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lizhe
 * @date 2020/9/30 14:24
 **/
@Component
public class ImageCompareWriter implements IFreeMarkerWriter {
    private String calcPosition(List<Integer[]> positions) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < positions.size(); i++) {
            Integer[] position = positions.get(i);
            sb.append("(");
            for (int j = 0; j < position.length; j++) {
                sb.append(position[j]);
                if (j != position.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append(")");
            if (i != positions.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    @Override
    public List<FreeMarker> convert(List<BaseEntity> entities, List<Message> messages) {
        String pre = this.getClass().getSimpleName().replace("Writer", "").toLowerCase() + "_";
        List<FreeMarker> freeMarkers = new ArrayList<>();
        for (BaseEntity baseEntity : entities) {
            FreeMarker freeMarker = new FreeMarker();
            ImageCompare imageCompare = (ImageCompare) baseEntity;
            Map<String, Object> map = new HashMap<>();
            map.put(FUNCTION_NAME, pre + imageCompare.getName());
            map.put(HANDLE_NAME, "image_compare");
            map.put(HANDLE_FUNCTION, "compare");
            map.put(HANDLE_FUNCTION_SUB, "handle_images");
            map.put(IMAGE_NAME, imageCompare.getImageName());
            map.put(COMPARE_TYPE, imageCompare.getCompareType().getValue());
            map.put(LIGHT, imageCompare.getTemplateLight());
            map.put(DARK, imageCompare.getTemplateDark());
            map.put(POSITION, calcPosition(imageCompare.getPositions()));
            map.put(SIMILARITY, String.valueOf(imageCompare.getSimilarity()));
            map.put(GRAY, imageCompare.getIsGray() ? "True" : "False");
            map.put(THRESHOLD, String.valueOf(imageCompare.getThreshold()));
            freeMarker.setParams(map);
            freeMarker.setComment(imageCompare.getComments());
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
