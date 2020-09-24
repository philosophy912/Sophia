package com.chinatsp.code.configure;

import com.chinatsp.code.enumeration.ConfigureTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @author lizhe
 * @date 2020/9/24 15:15
 **/
@Setter
@Getter
@ToString
public class ProjectConfig {

    private Map<ConfigureTypeEnum, String[]> map;
}
