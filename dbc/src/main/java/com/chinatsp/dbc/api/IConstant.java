package com.chinatsp.dbc.api;

/**
 * @author lizhe
 * @since V1.0.0 2019/11/3 21:58
 * 所有基础常量
 **/
public interface IConstant {

    String BLANK = " ";
    String GBK = "gbk";
    String UTF8 = "utf-8";
    String TRIM_BLANK = "\\s+";
    String Y_AXIS = "\\|";
    String QUOTATION = "\"";
    String SEMICOLON = ";";
    String PLUS = "+";
    String COMMA = ",";
    String POINT = ".";
    String COLON = ":";
    String COLON_CHINESE = "：";
    String AT = "@";
    String NULL = "";
    String YES = "YES";
    String YES_CHINESE = "是";
    String ONE = "1";
    String INT = "INT";
    String ENUM = "ENUM";
    String STRING = "STRING";
    String HEX = "HEX";
    /**
     * 环境变量
     */
    String EV = "EV_ ";
    /**
     * 消息
     */
    String BO = "BO_ ";
    /**
     * 信号
     */
    String SG = "SG_ ";
    /**
     * 信号描述only
     */
    String CM_ONLY = "CM_ ";
    /**
     * 信号描述
     */
    String CM = "CM_ SG_ ";
    /**
     * 信号值描述
     */
    String VAL = "VAL_ ";
    /**
     * 类型
     */
    String BA = "BA_ ";
    /**
     * 网络节点
     */
    String BU = "BU_ ";
    /**
     * 定义BA相关的类型 ENUM表示枚举类型
     */
    String BA_DEF = "BA_DEF_ ";
    /**
     * BA定义相关的引用
     */
    String BA_DEF_REL = "BA_DEF_REL_ ";
    /**
     * BA定义的默认值
     */
    String BA_DEF_DEF = "BA_DEF_DEF_ ";
    /**
     * BA定义相关的默认值的引用
     */
    String BA_DEF_DEF_REL = "BA_DEF_DEF_REL_ ";
    /**
     * VAL 表格
     */
    String VAL_TABLE = "VAL_TABLE_ ";

    String GEN_MSG_NR_OF_REPETITION = "GenMsgNrOfRepetition";
    String GEN_MSG_CYCLE_TIME_FAST = "GenMsgCycleTimeFast";
    String GEN_MSG_DELAY_TIME = "GenMsgDelayTime";
    String GEN_MSG_SEND_TYPE = "GenMsgSendType";
    String GEN_MSG_CYCLE_TIME = "GenMsgCycleTime";
    String V_FRAME_FORMAT = "VFrameFormat";
    String NM_MESSAGE = "NmMessage";
    String DIAG_STATE = "DiagState";
    String DIAG_REQUEST = "DiagRequest";
    String DIAG_RESPONSE = "DiagResponse";
    String STANDARD_CAN = "StandardCAN";
    String GEN_SIG_START_VALUE = "GenSigStartValue";

    /**
     * 诊断帧
     */
    String DIAG = "Diag";
    /**
     * 网络管理帧
     */
    String NM = "NM";
    /**
     * 普通帧
     */
    String NORMAL = "Normal";
    /**
     * 名字是否包含了请求字样
     */
    String REQUEST = "request";
    /**
     * 收到
     */
    String RECEIVE = "r";
    /**
     * 发送
     */
    String SEND = "s";
    /**
     * 大端小端方式
     */
    String MOTOROLA = "motorola";
    /**
     * LSB方式
     */
    String LSB = "lsb";
    /**
     * MSB方式
     */
    String MSB = "msg";
    /**
     * 是否float
     */
    String UNSIGNED = "unsigned";
}
