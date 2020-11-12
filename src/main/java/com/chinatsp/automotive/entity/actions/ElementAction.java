package com.chinatsp.automotive.entity.actions;

import com.chinatsp.automotive.entity.BaseEntity;
import com.chinatsp.automotive.enumeration.ElementOperationTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author lizhe
 * @date 2020/8/27 10:54
 **/
@Setter
@Getter
@ToString
public class ElementAction extends BaseEntity {
    /**
     * 操作类型OperationActionTypeEnum支持的操作类型
     */
    private ElementOperationTypeEnum operationActionType;
    /**
     * 要操作的元素名
     */
    private List<String> elements;
    /**
     * 滑动次数，仅对滑动有效
     */
    private Integer slideTimes;
}
