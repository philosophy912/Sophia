package com.chinatsp.code.checker.impl.action;

import com.chinatsp.code.checker.api.BaseChecker;
import com.chinatsp.code.checker.api.IEntityChecker;
import com.chinatsp.code.configure.Configure;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.actions.BatteryAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

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
public class BatteryActionChecker extends BaseChecker implements IEntityChecker {


    @Override
    public void check(List<BaseEntity> entities, Configure configure) {
        double maxVoltage = configure.getMaxVoltage();
        double minVoltage = configure.getMinVoltage();
        for (int i = 0; i < entities.size(); i++) {
            int index = i + 1;
            BatteryAction batteryAction = (BatteryAction) entities.get(i);
            String name = batteryAction.getClass().getName();
            checkUtils.checkBatteryOperator(batteryAction.getValues(), index, name, minVoltage, maxVoltage);
            checkUtils.checkPythonFunction(batteryAction.getName(), index, name);
        }
    }


}
