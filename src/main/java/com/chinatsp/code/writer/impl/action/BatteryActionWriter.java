package com.chinatsp.code.writer.impl.action;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.actions.BatteryAction;
import com.chinatsp.code.enumeration.BatteryOperationTypeEnum;
import com.chinatsp.code.writer.api.FreeMarker;
import com.chinatsp.code.writer.api.IFreeMarkerWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class BatteryActionWriter implements IFreeMarkerWriter {

    @Override
    public List<FreeMarker> convert(List<BaseEntity> entities) {
        List<FreeMarker> freeMarkers = new ArrayList<>();
        for (BaseEntity entity : entities) {
            FreeMarker freeMarker = new FreeMarker();
            BatteryAction batteryAction = (BatteryAction) entity;
            String functionName = batteryAction.getName();
            List<String> comments = batteryAction.getComments();
            // python操作句柄
            String handleName = batteryAction.getBatteryType().getName();
            BatteryOperationTypeEnum type = batteryAction.getBatteryOperationType();
            // 操作的函数
            String handleFunction = type.getName();
            int cycleTime = 0;
            if (type == BatteryOperationTypeEnum.ADJUST_VOLTAGE || type == BatteryOperationTypeEnum.CURVE) {
                cycleTime = batteryAction.getRepeatTimes();
            }
            List<String> params = new LinkedList<>();
            Double[] values = batteryAction.getValues();
            String curveFile = batteryAction.getCurveFile();
            // 电压曲线的时候values会为空，所以要做特殊处理
            if (null != values) {
                for (Double value : values) {
                    params.add(String.valueOf(value));
                }
            }
            String[] info = new String[5];
            // 函数名，句柄名， 句柄函数， 循环次数， 电压曲线文件
            info[0] = functionName;
            info[1] = handleName;
            info[2] = handleFunction;
            info[3] = String.valueOf(cycleTime);
            info[4] = curveFile;
            freeMarker.setInfo(info);
            freeMarker.setComment(comments);
            freeMarker.setParams(params);
            freeMarkers.add(freeMarker);
        }
        return freeMarkers;
    }
}
