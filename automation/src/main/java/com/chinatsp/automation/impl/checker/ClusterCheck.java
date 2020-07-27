package com.chinatsp.automation.impl.checker;

import com.chinatsp.automation.api.checker.BaseTestCaseCheck;
import com.chinatsp.automation.api.checker.IClusterCheck;
import com.chinatsp.automation.entity.actions.CanAction;
import com.chinatsp.automation.entity.compare.Compare;
import com.chinatsp.automation.entity.testcase.Cluster;
import com.philosophy.base.common.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.chinatsp.automation.api.IConstant.BMP;


/**
 * @author lizhe
 * @date 2020/5/28 10:43
 **/
@Slf4j
@Component
public class ClusterCheck extends BaseTestCaseCheck implements IClusterCheck {

    private static final String SHEET_NAME = "Sheet【测试用例】中";


    private void checkCompare(Compare compare, int index) {
        String dark = compare.getPictureTemplateDark();
        if (!dark.endsWith(BMP)) {
            String e = getError(SHEET_NAME, index) + "的DARK[" + dark + "]不是以bmp结尾，请检查";
            throw new RuntimeException(e);
        }
        String light = compare.getPictureTemplateLight();
        if (!dark.endsWith(BMP)) {
            String e = getError(SHEET_NAME, index) + "的LIGHT[" + light + "]不是以bmp结尾，请检查";
            throw new RuntimeException(e);
        }
        Integer[] x = compare.getX();
        Integer[] y = compare.getY();
        Integer[] width = compare.getWidth();
        Integer[] height = compare.getHeight();

        if (!(x.length == y.length && width.length == height.length)) {
            String e = getError(SHEET_NAME, index) + "的要对比的区域数量不正确，请检查";
            throw new RuntimeException(e);
        }
        int maxPercent = 100;
        if (compare.getThreshold() > maxPercent) {
            String e = getError(SHEET_NAME, index) + "图片比较阈值不能超过100，请检查";
            throw new RuntimeException(e);
        }
    }

    /**
     * 检查jsonName(即期望结果函数名)
     *
     * @param clusters 测试用例每一行
     */
    private void checkDuplicateJsonName(List<Cluster> clusters) {
        List<Pair<Integer, String>> convertList = new ArrayList<>();
        clusters.forEach(cluster -> {
            for (Pair<String, String> pair : cluster.getExpectFunctions()) {
                String functionName = pair.getSecond();
                if (!isFunctionCorrect(functionName)) {
                    String e = getError(SHEET_NAME, cluster.getId()) + "函数名[" + functionName + "]填写不正确";
                    throw new RuntimeException(e);
                }
                convertList.add(new Pair<>(cluster.getId(), pair.getSecond()));
            }
        });
        findDuplicateString(convertList);
    }


    @Override
    public void check(List<Cluster> clusters, List<CanAction> canActions) {
        // 检查函数名是否重名
        checkDuplicateName(clusters);
        clusters.forEach(cluster -> {
            int index = cluster.getId();
            checkObject(cluster, index, SHEET_NAME, canActions, null);
            // 检查期望结果
            checkExcept(cluster.getExpectFunctions(), index, SHEET_NAME);
            // 检查图像对比配置
            checkCompare(cluster.getCompare(), index);
        });
        checkDuplicateJsonName(clusters);
    }
}
