package com.chinatsp.code.entity.actions;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.enumeration.ScreenShotTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lizhe
 * @date 2020/8/27 10:07
 **/
@Setter
@Getter
@ToString
public class ScreenShotAction extends BaseEntity {
    /**
     * 截图类型，目前只支持ScreenShotTypeEnum描述的类型
     */
    private ScreenShotTypeEnum screenShotType;
    /**
     * 截图张数
     */
    private Integer count;
    /**
     * 截图名字，不需要包含后缀名
     */
    private String imageName;
    /**
     * todo 是否区域截图，暂不启用
     */
    private Boolean isArea;
}
