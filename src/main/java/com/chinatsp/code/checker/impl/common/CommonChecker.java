package com.chinatsp.code.checker.impl.common;

import com.chinatsp.code.checker.api.BaseChecker;
import com.chinatsp.code.checker.api.IChecker;
import com.chinatsp.code.configure.Configure;
import com.chinatsp.code.entity.BaseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CommonChecker extends BaseChecker implements IChecker {


    @Override
    public void check(Map<String, BaseEntity> entityMap) {

    }
}
