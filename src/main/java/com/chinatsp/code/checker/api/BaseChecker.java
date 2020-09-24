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
    protected int[] getResolution(Map<ConfigureTypeEnum, String[]> configure) {
        String[] qnx = configure.get(ConfigureTypeEnum.QNX_RESOLUTION);
        String[] android = configure.get(ConfigureTypeEnum.ANDROID_RESOLUTION);
        int qnxWidth = Integer.parseInt(qnx[0]);
        int qnxHeight = Integer.parseInt(qnx[1]);
        int androidWidth = Integer.parseInt(android[0]);
        int androidHeight = Integer.parseInt(android[1]);
        return new int[]{qnxWidth, qnxHeight, androidWidth, androidHeight};
    }
}
