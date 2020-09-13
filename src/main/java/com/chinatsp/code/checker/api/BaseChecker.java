package com.chinatsp.code.checker.api;

import com.chinatsp.code.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseChecker {
    @Autowired
    protected CheckUtils checkUtils;
}
