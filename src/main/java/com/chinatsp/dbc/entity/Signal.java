package com.chinatsp.dbc.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @author lizhe
 * @since V1.0.0 2019/10/31 22:08
 * signal对象
 **/
@Setter
@Getter
@ToString
public class Signal {
    /**
     * 信号名字
     */
    @JSONField(name = "name")
    private String name;
    /**
     * 信号长度
     */
    @JSONField(name = "signal_size")
    private Integer signalSize;
    /**
     * signal模式
     * Intel, Motorola
     * DBC解析的时候无法知道是LSB还是MSB
     */
    @JSONField(name = "byte_type")
    private Boolean byteType;
    /**
     * 信号的开始位
     */
    @JSONField(name = "start_bit")
    private Integer startBit;
    /**
     * value_type （* + =无符号， - =有符号*）
     * True表示无符号 False表示有符号
     */
    @JSONField(name = "is_sign")
    private Boolean isSign;
    /**
     * 值类型，是否是float类型
     */
    @JSONField(name = "is_float")
    private Boolean isFloat;
    /**
     * 计算因子
     */
    @JSONField(name = "factor")
    private Double factor;
    /**
     * 偏移量
     */
    @JSONField(name = "offset")
    private Double offset;
    /**
     * 最小值
     */
    @JSONField(name = "minimum")
    private Double minimum;
    /**
     * 最大值
     */
    @JSONField(name = "maximum")
    private Double maximum;
    /**
     * unit
     */
    @JSONField(name = "unit")
    private String unit;
    /**
     * 接收者
     */
    @JSONField(name = "receiver")
    private String receiver;
    /**
     * 备注
     */
    @JSONField(name = "comment")
    private String comment;
    /**
     * 键值对
     */
    @JSONField(name = "values")
    private Map<String, String> values;
    /**
     * 初始值
     */
    @JSONField(name = "start_value")
    private Integer sigStartValue;

}
