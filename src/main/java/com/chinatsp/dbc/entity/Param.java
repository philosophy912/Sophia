package com.chinatsp.dbc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @author lizhe
 */
@Setter
@Getter
@ToString
public class Param {
    /**
     * 报文名称
     */
    private Integer msgName;
    /**
     * 报文类型
     */
    private Integer msgType;
    /**
     * 报文标识符
     */
    private Integer msgId;
    /**
     * 扩展帧标识
     */
    private Integer extendedFrame;
    /**
     * 报文发送类型
     */
    private Integer msgSendType;
    /**
     * 报文周期时间
     */
    private Integer msgCycleTime;
    /**
     * 报文长度
     */
    private Integer msgLength;
    /**
     * 信号名称
     */
    private Integer signalName;
    /**
     * 信号描述
     */
    private Integer signalDescription;
    /**
     * 排列格式
     */
    private Integer byteOrder;
    /**
     * 起始字节
     */
    private Integer startByte;
    /**
     * 起始位
     */
    private Integer startBit;
    /**
     * 信号发送类型
     */
    private Integer signalSendType;
    /**
     * 信号长度
     */
    private Integer bitLength;
    /**
     * 数据类型
     */
    private Integer dateType;
    /**
     * 精度
     */
    private Integer resolution;
    /**
     * 偏移量
     */
    private Integer offset;
    /**
     * 物理最小值
     */
    private Integer signalMinValuePhys;
    /**
     * 物理最大值
     */
    private Integer signalMaxValuePhys;
    /**
     * 总线最小值
     */
    private Integer signalMinValueHex;
    /**
     * 总线最大值
     */
    private Integer signalMaxValueHex;
    /**
     * 初始值
     */
    private Integer initialValue;
    /**
     * 无效值
     */
    private Integer invalidValue;
    /**
     * 非使能值
     */
    private Integer inactiveValue;
    /**
     * 单位
     */
    private Integer unit;
    /**
     * 信号值描述
     */
    private Integer signalValueDescription;
    /**
     * 报文发送的快速周期
     */
    private Integer msgCycleTimeFast;
    /**
     * 报文快速发送的次数
     */
    private Integer msgNrOfReption;
    /**
     * 报文延时时间
     */
    private Integer msgDelayTime;
}
