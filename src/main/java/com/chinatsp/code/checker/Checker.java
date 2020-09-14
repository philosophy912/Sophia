package com.chinatsp.code.checker;

import com.chinatsp.code.checker.api.BaseChecker;
import com.chinatsp.code.checker.api.IChecker;
import com.chinatsp.code.configure.Configure;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.utils.CheckUtils;
import com.chinatsp.code.utils.ReaderUtils;
import com.chinatsp.dbc.entity.Message;
import com.philosophy.base.common.Reflect;
import com.philosophy.base.util.ClazzUtils;
import com.philosophy.character.util.CharUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static com.chinatsp.code.utils.Constant.CHECKER_PACKAGE_NAME;

/**
 * @author lizhe
 * @date 2020/9/14 12:37
 **/
@Service
public class Checker {
    @Resource
    private ReaderUtils readerUtils;
    @Resource
    private CheckUtils checkUtils;


    @SneakyThrows
    public void check(Map<String, List<BaseEntity>> map, List<Message> messages, Configure configure) {
        for (Map.Entry<String, List<BaseEntity>> entry : map.entrySet()) {
            String name = entry.getKey();
            String fullName = readerUtils.getFullClassName(CharUtils.upperCase(name) + "Checker", CHECKER_PACKAGE_NAME);
            Class<?> clazz = Class.forName(fullName);
            Object object = clazz.newInstance();
            Method method = clazz.getMethod("setCheckUtils", new Class[]{CheckUtils.class});
            method.invoke(object, checkUtils);
            method = clazz.getDeclaredMethod("check", new Class[]{Map.class, List.class, Configure.class});
            method.invoke(object, map, messages, configure);
        }
    }
}
