package com.philosophy.base.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhe
 * @date 2020-02-25 14:06
 */
@Setter
@Getter
@ApiModel
public class ResultResponse<T> {
    @ApiModelProperty(value = "响应状态")
    private boolean success = true;
    @ApiModelProperty(value = "返回数据")
    private List<T> data = new ArrayList<>(1);
    @ApiModelProperty(value = "分页信息")
    private EnvData envData = new EnvData();
}
