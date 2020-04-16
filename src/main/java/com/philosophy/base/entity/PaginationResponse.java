package com.philosophy.base.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020-02-25 14:09
 */
@Setter
@Getter
public final class PaginationResponse {

    private EnvData envData;
    private boolean success = true;
}
