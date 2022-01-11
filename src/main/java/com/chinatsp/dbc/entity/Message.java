package com.chinatsp.dbc.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


/**
 * @author lizhe
 * @since V1.0.0 2019/10/31 22:08
 * message对象
 **/
@Setter
@Getter
@ToString
public class Message {
    /**
     * message id
     */
    @JSONField(name = "id")
    private long id;
    /**
     * message name
     */
    @JSONField(name = "name")
    private String name;
    /**
     * message length
     */
    @JSONField(name = "length")
    private int length;
    /**
     * sender
     */
    @JSONField(name = "sender")
    private String sender;
    /**
     * signals
     */
    @JSONField(name = "signals")
    private List<Signal> signals;
    /**
     * 备注
     */
    @JSONField(name = "comment")
    private String comment;
    /**
     * 信号发送类型
     */
    @JSONField(name = "msg_send_type")
    private String msgSendType;
    /**
     * 信号周期
     */
    @JSONField(name = "msg_cycle_time")
    private Integer msgCycleTime;

    /**
     * 周期事件信号报文发送速度
     */
    @JSONField(name = "msg_cycle_time_fast")
    private Integer msgCycleTimeFast;
    /**
     * 报文快速发送的次数
     */
    @JSONField(name = "gen_msg_nr_of_repetition")
    private Integer genMsgNrOfRepetition = 0;
    /**
     * 报文延时时间
     */
    @JSONField(name = "msg_delay_time")
    private Integer msgDelayTime = 0;
    /**
     * 是否是网络管理帧
     */
    @JSONField(name = "nm_message")
    private boolean nmMessage;
    /**
     * 是否是诊断帧
     */
    @JSONField(name = "diag_state")
    private boolean diagState;
    /**
     * 诊断请求帧
     */
    @JSONField(name = "diag_request")
    private boolean diagRequest;
    /**
     * 诊断响应帧
     */
    @JSONField(name = "diag_response")
    private boolean diagResponse;
    /**
     * 是否是CAN FD
     */
    @Deprecated
    @JSONField(name = "is_can_fd")
    private boolean busType;
    /**
     * 是否标准帧(默认为标准帧)
     * DBC中有些是没有的
     */
    @JSONField(name = "is_standard_can")
    private boolean isStandardCan = true;



}
