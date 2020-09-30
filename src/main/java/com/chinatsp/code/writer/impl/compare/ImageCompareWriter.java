package com.chinatsp.code.writer.impl.compare;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.compare.ImageCompare;
import com.chinatsp.code.writer.api.FreeMarker;
import com.chinatsp.code.writer.api.IFreeMarkerWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lizhe
 * @date 2020/9/30 14:24
 **/
@Component
public class ImageCompareWriter implements IFreeMarkerWriter {
    @Override
    public List<FreeMarker> convert(List<BaseEntity> entities) {
        List<FreeMarker> freeMarkers = new ArrayList<>();
        for (BaseEntity baseEntity : entities) {
            FreeMarker freeMarker = new FreeMarker();
            ImageCompare imageCompare = (ImageCompare) baseEntity;
            String functionName = imageCompare.getName();
            List<String> comments = imageCompare.getComments();
            // python操作句柄
            String handleName = "image_compare";
            // 操作的函数
            String handleFunction1 = "image_compare";
            String handleFunction2 = "handle_images";
            String[] info = new String[4];
            info[0] = functionName;
            info[1] = handleName;
            info[2] = handleFunction1;
            info[3] = handleFunction2;
            List<String> params = new LinkedList<>();
            params.add(imageCompare.getImageName());
            params.add(imageCompare.getCompareType().getValue());
            params.add(imageCompare.getTemplateLight());
            params.add(imageCompare.getTemplateDark());
            List<Integer[]> positions = imageCompare.getPositions();
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
            params.add(sb.toString());
            params.add(String.valueOf(imageCompare.getSimilarity()));
            params.add(imageCompare.getIsGray() ? "True" : "False");
            params.add(String.valueOf(imageCompare.getThreshold()));
            freeMarker.setInfo(info);
            freeMarker.setComment(comments);
            freeMarker.setParams(params);
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
