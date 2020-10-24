package com.chinatsp.code.writer.impl.common;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.common.Common;
import com.chinatsp.code.writer.api.FreeMarker;
import com.chinatsp.code.writer.api.IFreeMarkerWriter;
import com.chinatsp.dbc.entity.Message;
import com.philosophy.base.common.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.chinatsp.code.utils.Constant.COMMA;
import static com.chinatsp.code.utils.Constant.EQUAL;

@Component
@Slf4j
public class CommonWriter implements IFreeMarkerWriter {

    String swipeElement = "element";
    String locator = "locator";


    private List<Pair<String, String>> parseParam(List<String> params, FreeMarker freeMarker) {
        List<Pair<String, String>> pairs = new LinkedList<>();
        for (String s : params) {
            String[] param = s.trim().split(EQUAL);
            String key = param[0];
            String value = param[1];
            if (key.equalsIgnoreCase(swipeElement) || key.equalsIgnoreCase(locator)) {
                parseMapParam(value, freeMarker, key);
            } else {
                pairs.add(new Pair<>(key, value));
            }
        }
        return pairs;
    }

    /**
     * 转换如 {"classname": "android.widget.ListView"}为List<pair<String, String>方式，便于写到ftlh上面
     *
     * @param string     {"classname": "android.widget.TextView"}
     * @param freeMarker 对象
     * @param type       类型，只支持swipe_element和locator
     */
    private void parseMapParam(String string, FreeMarker freeMarker, String type) {
        List<Pair<String, String>> pairs = new LinkedList<>();
        string = string.replace("{", "").replace("}", "").trim();
        String[] params = string.split(COMMA);
        for (String s : params) {
            // s = "resourceId":"com.chinatsp.vehicle:id/navigation_view"
            List<String> contents = new LinkedList<>();
            while (s.contains("\"")) {
                int index = s.indexOf("\"");
                // 去掉头部内容
                s = s.substring(index + "\"".length());
                //再次查找
                index = s.indexOf("\"");
                String content = s.substring(0, index);
                s = s.substring(index + 1);
                contents.add(content);
            }
            for (int i = 0; i < contents.size(); i += 2) {
                pairs.add(new Pair<>(contents.get(i), contents.get(i + 1)));
            }
        }
        if (type.equalsIgnoreCase(swipeElement)) {
            freeMarker.setSwipeElement(pairs);
        } else {
            freeMarker.setLocator(pairs);
        }
    }


    @Override
    public List<FreeMarker> convert(List<BaseEntity> entities, List<Message> messages) {
        List<FreeMarker> freeMarkers = new ArrayList<>();
        for (BaseEntity baseEntity : entities) {
            Common common = (Common) baseEntity;
            FreeMarker freeMarker = new FreeMarker();
            Map<String, String> map = new HashMap<>();
            map.put(FUNCTION_NAME, common.getName());
            map.put(HANDLE_NAME, common.getModuleName().getValue());
            map.put(HANDLE_FUNCTION, common.getFunctionName());
            freeMarker.setPairs(parseParam(common.getParams(), freeMarker));
            freeMarker.setParams(map);
            freeMarker.setComment(common.getComments());
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
