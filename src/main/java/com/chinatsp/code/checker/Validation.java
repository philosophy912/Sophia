package com.chinatsp.code.checker;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.dbc.entity.Message;
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
     * @param entities      实体集合
     * @param attributeName 属性名
     * @param maxHeight     最大的高
     * @param maxWidth      最大的宽
     */
    public void checkClickPoint(List<? extends BaseEntity> entities, String attributeName, int maxWidth, int maxHeight) {

    }

    /**
     * 检查屏幕数量是否符合要求
     *
     * @param entities      实体集合
     * @param attributeName 属性名
     * @param maxDisplay    最多的屏幕数量
     */
    public void checkDisplay(List<? extends BaseEntity> entities, String attributeName, int maxDisplay) {

    }

    /**
     * 检查继电器通道数量是否符合要求
     *
     * @param entities      实体集合
     * @param attributeName 属性名
     * @param maxChannel    最多的通道
     */
    public void checkRelayChannel(List<? extends BaseEntity> entities, String attributeName, int maxChannel) {

    }

    /**
     * 检查电源操作的值是否符合要求
     *
     * @param entities      实体集合
     * @param attributeName 属性名
     * @param maxValue      最大值
     * @param minValue      最小值
     */
    public void checkBatteryOperator(List<? extends BaseEntity> entities, String attributeName, int maxValue, int minValue) {

    }

    /**
     * 检查signal的名字和值是否正确
     *
     * @param entities      实体集合
     * @param attributeName 属性名
     * @param messages      CAN信号列表
     */
    public void checkSignalNameAndValue(List<? extends BaseEntity> entities, String attributeName, List<Message> messages) {

    }

    /**
     * 检查Message ID的值是否正确
     * 需要检查是否在messages中存在
     *
     * @param entities      实体集合
     * @param attributeName 属性名
     * @param messages      CAN信号列表
     */
    public void checkMessageId(List<? extends BaseEntity> entities, String attributeName, List<Message> messages) {

    }

    /**
     * 检查Android元素的定位符是否符合要求
     *
     * @param entities         实体集合
     * @param attributeName    属性名
     * @param availableLocator 可用的定位符集合
     */
    public void checkAndroidElement(List<? extends BaseEntity> entities, String attributeName, List<String> availableLocator) {

    }

    /**
     * 检查图片对比中的相似度是否正确
     *
     * @param entities      实体集合
     * @param attributeName 可用的定位符集合
     */
    public void checkSimilarity(List<? extends BaseEntity> entities, String attributeName) {

    }

}
