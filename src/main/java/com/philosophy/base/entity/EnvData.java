package com.philosophy.base.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020-02-25 14:07
 */
@Setter
@Getter
@ApiModel
public final class EnvData {
    @ApiModelProperty(value = "响应码")
    private Integer responseStatusCode = 200;
    @ApiModelProperty(value = "响应消息")
    private String responseStatusMessage;
    @ApiModelProperty(value = "第几页")
    private Integer pageNo = 1;
    @ApiModelProperty(value = "每页数量")
    private Integer pageSize = 20;
    @ApiModelProperty(value = "总条数")
    private Integer totalRows = 0;
    @ApiModelProperty(value = "总页数")
    private Integer totalPages = 0;

}
