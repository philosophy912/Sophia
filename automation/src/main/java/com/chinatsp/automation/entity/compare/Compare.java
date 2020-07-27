package com.chinatsp.automation.entity.compare;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lizhe
 * @since V1.0.0 2019/12/22 10:37
 * 图像对比对象
 **/
@Setter
@Getter
@ToString
public class Compare {
    /**
     * 亮图
     */
    @JSONField(name = "template_light")
    private String pictureTemplateLight;
    /**
     * 暗图
     */
    @JSONField(name = "template_dark")
    private String pictureTemplateDark;
    /**
     * 截图张数
     */
    @JSONField(name = "shot_count")
    private Integer screenShotCount = 1;
    /**
     * 起始点x
     */
    @JSONField(name = "x")
    private Integer[] x;
    /**
     * 起始点y
     */
    @JSONField(name = "y")
    private Integer[] y;
    /**
     * 宽度width
     */
    @JSONField(name = "width")
    private Integer[] width;
    /**
     * 高度height
     */
    @JSONField(name = "height")
    private Integer[] height;
    /**
     * 是否灰度对比
     */
    @JSONField(name = "gray")
    private Boolean isGray = false;
    /**
     * 灰度对比的二值化，默认240d
     */
    @JSONField(name = "gray_threshold")
    private Double grayThreshold = 240d;
    /**
     * 图像对比阈值, 默认90
     */
    @JSONField(name = "threshold")
    private Double threshold = 90d;
    /**
     * 是否区域对比， 默认否
     */
    @JSONField(name = "is_area")
    private Boolean isArea = false;
    /**
     * 保留颜色, 默认值{-1, -1, -1}
     */
    @JSONField(name = "hold_rgb")
    private Integer[] holdRgb = new Integer[]{-1, -1, -1};

}
