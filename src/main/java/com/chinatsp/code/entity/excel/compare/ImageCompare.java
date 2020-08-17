package com.chinatsp.code.entity.excel.compare;

import com.chinatsp.code.entity.types.CompareTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author lizhe
 * @date 2020/8/14 15:07
 **/
@Setter
@Getter
@ToString
public class ImageCompare {
    /**
     * 序号
     */
    private Integer id;
    /**
     * 函数名
     */
    private String name;
    /**
     * 对比类型
     */
    private String compareType;
    /**
     * 要对比的图片
     */
    private String image;
    /**
     * 原始图片(亮图)
     */
    private String templateLight;
    /**
     * 原始图片(暗图)
     */
    private String templateDark;
    /**
     * 图片对比区域
     */
    private List<Integer[]> position;
    /**
     * 相似度
     */
    private Double similarity;
    /**
     * 是否灰度对比
     */
    private Boolean isGray;
    /**
     * 灰度对比二值化值
     */
    private Integer grayThreshold;
}
