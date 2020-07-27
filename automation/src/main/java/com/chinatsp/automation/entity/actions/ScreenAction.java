package com.chinatsp.automation.entity.actions;

import com.chinatsp.automation.entity.base.FunctionEntity;
import com.philosophy.base.common.Pair;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author lizhe
 * @date 2020/5/26 16:09
 **/
@Setter
@Getter
@ToString
public class ScreenAction extends FunctionEntity {
    /**
     * 屏幕序号
     */
    private Integer displayIndex;
    /**
     * 坐标点，以 x-y以及列表方式显示
     * TIPS: String, String的原因在于底层代码通用适配，所以没有变成Integer,Integer方式
     */
    private List<Pair<String, String>> position;
    /**
     * 持续时间
     */
    private String continueTime;
}
