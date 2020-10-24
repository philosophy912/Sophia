package com.chinatsp.code.entity.common;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.enumeration.CommonTypeEnum;
import com.chinatsp.code.enumeration.ModuleTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author lizhe
 * @date 2020/8/27 15:52
 **/
@Setter
@Getter
@ToString
public class Common extends BaseEntity {
    /**
     * 模块名
     */
    private ModuleTypeEnum moduleName;
    /**
     * 函数名
     */
    private String functionName;
    /**
     * 参数
     */
    private List<String> params;

}
