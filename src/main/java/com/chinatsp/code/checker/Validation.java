package com.chinatsp.code.checker;

import com.chinatsp.code.entity.BaseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 校验所有的数据是否符合预期
 *
 * @author lizhe
 * @date 2020/8/28 9:14
 **/
@Component
public class Validation {

    /**
     * 检查实体类中的某个属性的值是否符合python对于函数的命名规范
     *
     * @param entities      实体集合
     * @param attributeName 属性名
     */
    public void checkPythonFunction(List<? extends BaseEntity> entities, String attributeName) {

    }

    /**
     * 检查点击的点是否在屏幕范围内
     *
     * @param entities 实体集合
     * @param attributeName    属性名
     * @param maxHeight 最大的高
     * @param maxWidth 最大的宽
     */
    public void checkClickPoint(List<? extends BaseEntity> entities, String attributeName, int maxWidth, int maxHeight) {

    }
    /**
     * 检查屏幕数量是否符合要求
     *
     * @param entities 实体集合
     * @param attributeName    属性名
     * @param maxDisplay 最多的屏幕数量
     */
    public void checkDisplay(List<? extends BaseEntity> entities, String attributeName, int maxDisplay){

    }
    /**
     * 检查继电器通道数量是否符合要求
     *
     * @param entities 实体集合
     * @param attributeName    属性名
     * @param maxChannel 最多的通道
     */
    public void checkRelayChannel(List<? extends BaseEntity> entities, String attributeName, int maxChannel){

    }


}
