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

    /**
     * 获取对应的实体类的集合，如读取BatteryAction表格，则返回读取到的BatteryActionEntity的集合
     *
     * @param map   从excel读取到的所有Sheet的集合，一个Sheet表示一个Entity集合
     * @param clazz 实体类名
     * @return 实体类的集合
     */
    protected List<BaseEntity> getEntity(Map<String, List<BaseEntity>> map, Class<?> clazz) {
        return map.get(CharUtils.lowerCase(CharUtils.lowerCase(clazz.getSimpleName())));
    }

    /**
     * 获取屏幕分辨率
     * 当未传入了相关数据的时候，所有的数据都返回了-1否则变成相关数据，而相关数据的读取主要是Configure配置表中读取到的数据
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
