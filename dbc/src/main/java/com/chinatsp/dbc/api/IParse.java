package com.chinatsp.dbc.api;

import com.chinatsp.dbc.entity.Message;

import java.nio.file.Path;
import java.util.List;

/**
 * @author lizhe
 * @date 2020-05-31 11:51
 */
public interface IParse extends IConstant{
    /**
     * 把文件转成messages对象
     * @param path 文件
     * @return messages
     */
    List<Message> parse(Path path);
}
