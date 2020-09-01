package com.chinatsp.code;

import com.chinatsp.code.enumeration.BatteryOperationTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lizhe
 * @date 2020/9/1 15:12
 **/
@Setter
@Getter
@ToString
public class Entity {

    private BatteryOperationTypeEnum batteryOperationType;
}
