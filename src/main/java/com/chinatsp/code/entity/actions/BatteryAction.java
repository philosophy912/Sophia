package com.chinatsp.code.entity.actions;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.enumeration.BatteryOperationTypeEnum;
import com.chinatsp.code.enumeration.BatteryTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author lizhe
 * @date 2020/8/27 11:25
 **/
@Setter
@Getter
@ToString
public class BatteryAction extends BaseEntity {
    /**
     * 电源类型
     */
    private BatteryTypeEnum batteryType;
    /**
     * 电源操作类型
     */
    private BatteryOperationTypeEnum batteryOperationType;
    /**
     * 要设置的值
     * 调节电压则以-分割，如12-18-0.1-5
     */
    private List<Double> values;
    /**
     * 重复次数
     */
    private Integer repeatTimes;
    /**
     * 电压曲线文件
     */
    private String curveFile;
}
