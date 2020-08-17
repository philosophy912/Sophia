package com.chinatsp.code.entity.excel.actions;

import com.chinatsp.code.entity.types.SystemEnum;
import com.chinatsp.code.entity.types.TouchActionEnum;
import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author lizhe
 * @date 2020/8/12 14:53
 **/
@Setter
@Getter
@ToString
public class TouchAction {
    /**
     * 序号
     */
    private Integer id;
    /**
     * 操作类型
     */
    private String type;
    /**
     * 屏幕操作类型
     */
    private String touchAction;
    /**
     * 屏幕序号
     */
    private Integer displayId;
    /**
     * 操作的坐标点
     */
    private List<Integer[]> positions;
    /**
     * 滑动持续时间
     */
    private Double continueTime;
    /**
     * 备注
     */
    private String comments;

}
