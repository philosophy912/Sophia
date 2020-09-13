package com.chinatsp.code.reader.api;


import com.chinatsp.code.utils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseType {
    @Autowired
    protected ConvertUtils convertUtils;
}
