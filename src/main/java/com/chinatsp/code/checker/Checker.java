package com.chinatsp.code.checker;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.enumeration.ConfigureTypeEnum;
import com.chinatsp.code.utils.CheckUtils;
import com.chinatsp.code.utils.ReaderUtils;
import com.chinatsp.dbc.entity.Message;
import com.chinatsp.dbc.impl.DbcParser;
import com.philosophy.base.util.StringsUtils;
import com.philosophy.character.util.CharUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class Checker {
    @Resource
    private ReaderUtils readerUtils;
    @Resource
    private CheckUtils checkUtils;
    @Resource
    private DbcParser dbcParser;


    @SneakyThrows
    public List<Message> check(Map<String, List<BaseEntity>> map, Map<ConfigureTypeEnum, String> configure, String folder) {
        // 可以允许DBC文件不存在，但是就不能够有检查CAN的部分
        String dbcFile = configure.get(ConfigureTypeEnum.DBC_FILE);
        List<Message> messages = null;
        if(StringsUtils.isNotEmpty(dbcFile)){
            Path dbcPath = Paths.get(folder, dbcFile);
            if (!Files.exists(dbcPath)) {
                String error = "DBC文件[" + dbcFile + "]不存在，请检查配置的路径是否正确";
                throw new RuntimeException(error);
            }
            messages = dbcParser.parse(dbcPath);
        }
        for (Map.Entry<String, List<BaseEntity>> entry : map.entrySet()) {
            String name = entry.getKey();
            String fullName = readerUtils.getFullClassName(CharUtils.upperCase(name) + "Checker", CHECKER_PACKAGE_NAME);
            log.debug("try to check with {}", fullName);
            Class<?> clazz = Class.forName(fullName);
            Object object = clazz.newInstance();
            Method method = clazz.getMethod("setCheckUtils", CheckUtils.class);
            method.invoke(object, checkUtils);
            method = clazz.getDeclaredMethod("check", Map.class, List.class, Map.class);
            method.invoke(object, map, messages, configure);
        }
        return messages;
    }
}
