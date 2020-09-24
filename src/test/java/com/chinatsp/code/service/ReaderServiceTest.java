package com.chinatsp.code.service;

import com.chinatsp.code.BaseTestUtils;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.enumeration.ConfigureTypeEnum;
import com.chinatsp.code.service.api.IReadService;
import com.philosophy.base.common.Pair;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


/**
 * @author lizhe
 * @date 2020/9/15 13:58
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class ReaderServiceTest {
    @Autowired
    private IReadService readerService;

    @Test
    void read() {
        Path path = Paths.get(BaseTestUtils.getFileFolder(), "template.xlsx");
        Pair<Map<String, List<BaseEntity>>, Map<ConfigureTypeEnum, String[]>> content = readerService.read(path);
        System.out.println(content);
        Assert.assertNotNull(content);
    }
}