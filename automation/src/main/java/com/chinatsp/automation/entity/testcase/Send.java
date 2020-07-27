package com.chinatsp.automation.entity.testcase;

import com.chinatsp.automation.entity.base.TestCase;
import com.chinatsp.automation.entity.compare.CanCompare;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 空调屏点击测试
 * @author lizhe
 * @date 2020/5/26 16:12
 **/
@Setter
@Getter
@ToString
public class Send extends TestCase {
    /**
     * Can消息对比
     */
    private List<CanCompare> canCompares;
}
