package com.chinatsp.code.checker.api;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.enumeration.ConfigureTypeEnum;
import com.chinatsp.code.utils.CheckUtils;
import com.philosophy.character.util.CharUtils;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Setter
public abstract class BaseChecker {

    @Autowired
    protected CheckUtils checkUtils;


    protected List<BaseEntity> getEntity(Map<String, List<BaseEntity>> map, Class<?> clazz) {
        return map.get(CharUtils.lowerCase(CharUtils.lowerCase(clazz.getSimpleName())));
    }

    /**
     * 获取屏幕分辨率
     *
     * @param configure 设置的参数
     * @return 屏幕宽高信息
     */
    protected int[] getResolution(Map<ConfigureTypeEnum, String> configure) {
        int qnxWidth = Integer.parseInt(configure.get(ConfigureTypeEnum.QNX_RESOLUTION_WIDTH));
        int qnxHeight = Integer.parseInt(configure.get(ConfigureTypeEnum.QNX_RESOLUTION_HEIGHT));
        int androidWidth = Integer.parseInt(configure.get(ConfigureTypeEnum.ANDROID_RESOLUTION_WIDTH));
        int androidHeight = Integer.parseInt(configure.get(ConfigureTypeEnum.ANDROID_RESOLUTION_HEIGHT));
        return new int[]{qnxWidth, qnxHeight, androidWidth, androidHeight};
    }
}
