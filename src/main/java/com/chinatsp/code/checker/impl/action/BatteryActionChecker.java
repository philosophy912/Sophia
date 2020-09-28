package com.chinatsp.code.checker.impl.action;

import com.chinatsp.code.checker.api.BaseChecker;
import com.chinatsp.code.checker.api.IChecker;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.actions.BatteryAction;
import com.chinatsp.code.enumeration.BatteryOperationTypeEnum;
import com.chinatsp.code.enumeration.BatteryTypeEnum;
import com.chinatsp.code.enumeration.ConfigureTypeEnum;
import com.chinatsp.dbc.entity.Message;
import com.philosophy.character.util.CharUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 检查内容：
 * 1. 电源操作的函数名是否符合要求
 * 2. 操作值是否符合要求
 * 仅支持数字
 * 若为设置电压电流则为单个数字
 * 若调节电压则以-分割，如12-18-0.1-5
 * 表示12V调整到18V，步长0.1V，调节间隔时间5秒
 */
@Component
@Slf4j
public class BatteryActionChecker extends BaseChecker implements IChecker {


    @Override
    public void check(Map<String, List<BaseEntity>> map, List<Message> messages, Map<ConfigureTypeEnum, String> configure) {
        List<BaseEntity> entities = getEntity(map, BatteryAction.class);
        double minVoltage = Double.parseDouble(configure.get(ConfigureTypeEnum.VOLTAGE_MIN));
        double maxVoltage = Double.parseDouble(configure.get(ConfigureTypeEnum.VOLTAGE_MAX));
        for (int i = 0; i < entities.size(); i++) {
            int index = i + 1;
            BatteryAction batteryAction = (BatteryAction) entities.get(i);
            String name = batteryAction.getClass().getSimpleName();
            // 检查操作是否符合要求
            // 若为设置电压电流则为单个数字
            // 若调节电压则以-分割，如12-18-0.1-5
            BatteryOperationTypeEnum type = batteryAction.getBatteryOperationType();
            Double[] doubles = batteryAction.getValues();
            log.debug("adjust voltage is " + Arrays.toString(doubles));
            if (type == BatteryOperationTypeEnum.SET_CURRENT || type == BatteryOperationTypeEnum.SET_VOLTAGE) {
                checkUtils.checkBatteryValue(doubles, index, name, minVoltage, maxVoltage);
            } else if (type == BatteryOperationTypeEnum.ADJUST_VOLTAGE) {
                checkUtils.checkBatteryAdjust(doubles, index, name, minVoltage, maxVoltage);
            } else if (type == BatteryOperationTypeEnum.CURVE) {
                if (batteryAction.getBatteryType() != BatteryTypeEnum.KONSTANTER) {
                    String error = "Sheet[" + CharUtils.upperCase(name) + "]的第" + index + "行数据填写错误，电压曲线仅支持KONSTANTER电源";
                    throw new RuntimeException(error);
                }
            }
            // 检查名字是否符合python命名规范
            checkUtils.checkPythonFunction(batteryAction.getName(), index, name);
        }
        // 检查函数名是否有重名
        checkUtils.findDuplicate(entities, BatteryAction.class.getSimpleName());
    }


}
