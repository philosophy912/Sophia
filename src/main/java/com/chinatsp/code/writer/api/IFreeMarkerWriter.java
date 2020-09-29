package com.chinatsp.code.writer.api;

import com.chinatsp.code.entity.BaseEntity;

import java.util.List;

public interface IFreeMarkerWriter {
    /**
     * 把Sheet对象转换成便于写入freemarker的内容
     *
     * @param entities 对象
     * @return freemarker函数内容
     */
    List<FreeMarker> convert(List<BaseEntity> entities);

}
