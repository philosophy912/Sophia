package com.chinatsp.code.entity.actions;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.enumeration.DeviceTpeEnum;
import com.chinatsp.code.enumeration.OperationActionTypeEnum;
import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author lizhe
 * @date 2020/8/27 10:10
 **/
@Setter
@Getter
@ToString
public class ScreenOperationAction extends BaseEntity {
    /**
     * 屏幕类型, 只支持DeviceTpeEnum描述的类型
     */
    private DeviceTpeEnum deviceTpeEnum;
    /**
     * 操作类型OperationActionTypeEnum支持的操作类型
     */
    private OperationActionTypeEnum operationActionType;
    /**
     * 屏幕序号
     */
    private Integer screenIndex;
    /**
     * 坐标点
     */
    private List<Pair<Integer, Integer>> points;
    /**
     * 持续时间
     * 仅滑动和长按支持持续时间
     */
    private Double continueTimes;

}
