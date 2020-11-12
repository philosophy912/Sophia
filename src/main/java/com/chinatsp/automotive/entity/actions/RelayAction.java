package com.chinatsp.automotive.entity.actions;

import com.chinatsp.automotive.entity.BaseEntity;
import com.chinatsp.automotive.enumeration.RelayOperationTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lizhe
 * @date 2020/8/27 11:19
 **/
@Setter
@Getter
@ToString
public class RelayAction extends BaseEntity {
    /**
     * 继电器操作类型
     */
    private RelayOperationTypeEnum relayOperationType;
    /**
     * 继电器操作通道， 仅适用于ON, OFF
     */
    private Integer channelIndex;
}
