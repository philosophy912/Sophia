package com.chinatsp.code.writer.impl.action;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.actions.BatteryAction;
import com.chinatsp.code.enumeration.BatteryOperationTypeEnum;
import com.chinatsp.code.writer.api.FreeMarker;
import com.chinatsp.code.writer.api.IFreeMarkerWriter;
import com.chinatsp.dbc.entity.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BatteryActionWriter implements IFreeMarkerWriter {

    @Override
    public List<FreeMarker> convert(List<BaseEntity> entities, List<Message> messages) {
        List<FreeMarker> freeMarkers = new ArrayList<>();
        for (BaseEntity entity : entities) {
            FreeMarker freeMarker = new FreeMarker();
            BatteryAction batteryAction = (BatteryAction) entity;
            BatteryOperationTypeEnum type = batteryAction.getBatteryOperationType();
            Double[] values = batteryAction.getValues();
            Map<String, String> map = new HashMap<>();
            map.put(FUNCTION_NAME, batteryAction.getName());
            map.put(HANDLE_NAME, batteryAction.getBatteryType().getName());
            map.put(HANDLE_FUNCTION, type.getName());
            map.put(CYCLE_TIME, String.valueOf(batteryAction.getRepeatTimes()));
            if(type == BatteryOperationTypeEnum.SET_CURRENT){
                map.put(CURRENT, String.valueOf(values[0]));
            }else if(type == BatteryOperationTypeEnum.SET_VOLTAGE){
                map.put(VOLTAGE, String.valueOf(values[0]));
            }else if (type == BatteryOperationTypeEnum.ADJUST_VOLTAGE){
                map.put(START,String.valueOf(values[0]));
                map.put(END,String.valueOf(values[1]));
                map.put(STEP,String.valueOf(values[2]));
                map.put(INTERVAL,String.valueOf(values[3]));
            }else{
                map.put(CURVE, batteryAction.getCurveFile());
            }
            freeMarker.setParams(map);
            freeMarker.setComment(batteryAction.getComments());
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
