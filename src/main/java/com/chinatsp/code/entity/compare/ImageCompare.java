package com.chinatsp.code.entity.compare;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.enumeration.CompareTypeEnum;
import com.chinatsp.code.enumeration.PositionEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @author lizhe
 * @date 2020/8/27 13:10
 **/
@Setter
@Getter
@ToString
public class ImageCompare extends BaseEntity {
    /**
     * 图片对比类型
     * 仅支持CompareTypeEnum描述的类型
     */
    private CompareTypeEnum compareType;
    /**
     * 图片名字，不需要扩展名。
     * 图片为截图而来
     */
    private String imageName;
    /**
     * 模板亮图
     */
    private String templateLight;
    /**
     * 模板暗图
     */
    private String templateDark;
    /**
     * 图片对比区域
     */
    private List<Map<PositionEnum, Integer>> positions;
    /**
     * 相似度
     */
    private Float similarity = 0.7f;
    /**
     * 是否灰度对比
     */
    private Boolean isGray = false;
    /**
     * 灰度二值化的阈值
     */
    private Integer threshold = 240;
}
