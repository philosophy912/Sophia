package com.chinatsp.code.entity.actions;

import com.chinatsp.code.entity.types.SystemEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lizhe
 * @date 2020/8/12 14:22
 **/
@Setter
@Getter
@ToString
public class Screenshot  {
    /**
     * 序号
     */
    private Integer id;
    /**
     * 操作类型
     */
    private SystemEnum type;
    /**
     * 函数名
     */
    private String functionName;
    /**
     * 截图张数
     */
    private Integer count;
    /**
     * 截图名字
     */
    private String screenshotName;
    /**
     * 是否区域截图
     */
    private Boolean isArea;
    /**
     * 备注
     */
    private String comments;
}
