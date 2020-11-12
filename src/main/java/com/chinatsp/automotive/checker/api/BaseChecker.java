package com.chinatsp.automotive.checker.api;

import com.chinatsp.automotive.entity.BaseEntity;
import com.chinatsp.automotive.enumeration.ConfigureTypeEnum;
import com.chinatsp.automotive.utils.CheckUtils;
import com.philosophy.character.util.CharUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Setter
@Slf4j
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
        int qnxWidth = -1;
        int qnxHeight = -1;
        int androidWidth = -1;
        int androidHeight = -1;
        try {
            qnxWidth = Integer.parseInt(configure.get(ConfigureTypeEnum.QNX_RESOLUTION_WIDTH));
        } catch (NumberFormatException e) {
            log.debug("null input be found" + e.getMessage());
        }
        try {
            qnxHeight = Integer.parseInt(configure.get(ConfigureTypeEnum.QNX_RESOLUTION_HEIGHT));
        } catch (NumberFormatException e) {
            log.debug("null input be found" + e.getMessage());
        }
        try {
            androidWidth = Integer.parseInt(configure.get(ConfigureTypeEnum.ANDROID_RESOLUTION_WIDTH));
        } catch (NumberFormatException e) {
            log.debug("null input be found" + e.getMessage());
        }
        try {
            androidHeight = Integer.parseInt(configure.get(ConfigureTypeEnum.ANDROID_RESOLUTION_HEIGHT));
        } catch (NumberFormatException e) {
            log.debug("null input be found" + e.getMessage());
        }
        return new int[]{qnxWidth, qnxHeight, androidWidth, androidHeight};
    }
}
