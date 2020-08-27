package com.chinatsp.code.entity.actions;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.enumeration.ScreenTypeEnum;
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
     * 屏幕类型, 只支持ScreenTypeEnum描述的类型
     */
    private ScreenTypeEnum screenType;
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
