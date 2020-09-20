package com.chinatsp.code.checker;

import com.chinatsp.code.configure.Configure;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.utils.CheckUtils;
import com.chinatsp.code.utils.ReaderUtils;
import com.chinatsp.dbc.entity.Message;
import com.chinatsp.dbc.impl.DbcParser;
import com.philosophy.character.util.CharUtils;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    @Resource
    private DbcParser dbcParser;


    @SneakyThrows
    public void check(Map<String, List<BaseEntity>> map, Configure configure) {
        String dbcFile = configure.getDbcFile();
        Path dbcPath = Paths.get(dbcFile);
        if (!Files.exists(dbcPath)) {
            String error = "DBC文件[" + dbcFile + "]不存在，请检查配置的路径是否正确";
            throw new RuntimeException(error);
        }
        List<Message> messages = dbcParser.parse(Paths.get(configure.getDbcFile()));
        for (Map.Entry<String, List<BaseEntity>> entry : map.entrySet()) {
            String name = entry.getKey();
            String fullName = readerUtils.getFullClassName(CharUtils.upperCase(name) + "Checker", CHECKER_PACKAGE_NAME);
            Class<?> clazz = Class.forName(fullName);
            Object object = clazz.newInstance();
            Method method = clazz.getMethod("setCheckUtils", CheckUtils.class);
            method.invoke(object, checkUtils);
            method = clazz.getDeclaredMethod("check", Map.class, List.class, Configure.class);
            method.invoke(object, map, messages, configure);
        }
    }
}
