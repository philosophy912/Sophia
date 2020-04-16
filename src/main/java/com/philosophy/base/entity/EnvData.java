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
public final class EnvData {
    private Integer responseStatusCode = 200;
    private String responseStatusMessage;
    private Integer pageNo = 1;
    private Integer pageSize = 20;
    private Integer totalRows = 0;
    private Integer totalPages = 0;

}
