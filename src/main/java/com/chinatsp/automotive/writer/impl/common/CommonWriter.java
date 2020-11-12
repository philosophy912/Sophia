package com.chinatsp.automotive.writer.impl.common;

import com.chinatsp.automotive.entity.BaseEntity;
import com.chinatsp.automotive.entity.common.Common;
import com.chinatsp.automotive.writer.api.FreeMarker;
import com.chinatsp.automotive.writer.api.IFreeMarkerWriter;
import com.chinatsp.dbc.entity.Message;
import com.philosophy.base.common.Pair;
import com.philosophy.base.common.Triple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.chinatsp.automotive.utils.Constant.COMMA;
import static com.chinatsp.automotive.utils.Constant.EQUAL;

@Component
@Slf4j
public class CommonWriter implements IFreeMarkerWriter {


    /**
     * 转换如 {"classname": "android.widget.ListView"}为List<pair<String, String>方式，便于写到ftlh上面
     *
     * @param string {"classname": "android.widget.TextView"}
     */
    private List<Pair<String, String>> parseDictParam(String string) {
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
        return pairs;
    }


    private List<Triple<String, Object, String>> parseParam(List<String> params) {
        List<Triple<String, Object, String>> triples = new LinkedList<>();
        for (String s : params) {
            boolean flag = false;
            String[] param = s.trim().split(EQUAL);
            String key = param[0];
            String value = param[1];
            if (value.contains("{") && value.contains("}")) {
                // 解析出来字典类型的所有数据
                List<Pair<String, String>> pairs = parseDictParam(value);
                triples.add(new Triple<>(key, pairs, "dict"));
            } else {
                // 只要有双引号就表示字符串类型
                if (value.contains("\"")) {
                    flag = true;
                }
                triples.add(new Triple<>(key, value.replace("\"", ""), flag ? "true" : "false"));
            }
        }
        return triples;
    }

    @Override
    public List<FreeMarker> convert(List<BaseEntity> entities, List<Message> messages) {
        String pre = this.getClass().getSimpleName().replace("Writer", "").toLowerCase() + "_";
        List<FreeMarker> freeMarkers = new ArrayList<>();
        for (BaseEntity baseEntity : entities) {
            Common common = (Common) baseEntity;
            FreeMarker freeMarker = new FreeMarker();
            Map<String, Object> map = new HashMap<>();
            map.put(FUNCTION_NAME, pre + common.getName());
            map.put(HANDLE_NAME, common.getModuleName().getValue());
            map.put(HANDLE_FUNCTION, common.getFunctionName());
            freeMarker.setParamList(parseParam(common.getParams()));
            freeMarker.setParams(map);
            freeMarker.setComment(common.getComments());
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
