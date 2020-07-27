package com.chinatsp.automation.impl.checker;

import com.chinatsp.automation.api.checker.BaseCheck;
import com.chinatsp.automation.api.checker.ICanActionsCheck;
import com.chinatsp.automation.entity.actions.CanAction;
import com.chinatsp.dbc.entity.Message;
import com.chinatsp.dbc.entity.Signal;
import com.philosophy.base.common.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.chinatsp.automation.api.IConstant.ACTION;
import static com.chinatsp.automation.api.IConstant.CONDITION;

/**
 * @author lizhe
 * @date 2020-05-27 21:43
 */
@Slf4j
@Component
public class CanActionsCheck extends BaseCheck implements ICanActionsCheck {


    private static final String SHEET_NAME = "Sheet【动作】中";

    /**
     * 检查某个类别的函数名是否重名
     *
     * @param canActions param每一行
     * @param category   类别
     */
    private void checkCategoriesDuplicateName(List<CanAction> canActions, String category) {
        List<Pair<Integer, String>> convertList = new ArrayList<>();
        canActions.stream()
                .filter(canAction -> canAction.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList())
                .forEach(action -> convertList.add(new Pair<>(action.getId(), action.getFunctionName())));
        findDuplicateString(convertList);
    }


    private void checkActions(List<CanAction> canActions, String category, String functionName, int index) {
        // 检查函数名是否存在于该类别的函数名中
        long count = canActions.stream()
                .filter(canAction -> canAction.getCategory().equalsIgnoreCase(category)
                        && canAction.getFunctionName().equals(functionName)).count();
        if (count != 1) {
            String e = getError(SHEET_NAME, index) + "依赖中的函数名[" + functionName + "]不正确";
            throw new RuntimeException(e);
        }
    }

    /**
     * 检查依赖的是否正确
     *
     * @param canActions   param每一行
     * @param dependencies 依赖
     * @param index        序号
     */
    private void checkDependency(List<CanAction> canActions, List<Pair<String, String>> dependencies, int index) {
        for (Pair<String, String> pair : dependencies) {
            String type = pair.getFirst();
            String functionName = pair.getSecond();
            // 检查依赖的类别是否正确
            if (type.equalsIgnoreCase(CONDITION)) {
                checkActions(canActions, CONDITION, functionName, index);
            } else if (type.equalsIgnoreCase(ACTION)) {
                checkActions(canActions, ACTION, functionName, index);
            } else {
                String e = getError(SHEET_NAME, index) + "的依赖有问题，只允许[" + CONDITION + "和" + ACTION + "], " +
                        "当前设置的是[" + type + "]";
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 检查signal是否正确
     *
     * @param signals signals的列表
     * @param index   第几行
     */
    private void checkSignals(CanAction canAction, List<Pair<String, String>> signals, List<Message> messages, int index) {
        for (Pair<String, String> pair : signals) {
            String sigName = pair.getFirst();
            log.trace("check sigName is {}", signals);
            boolean flag = false;
            for (Message message : messages) {
                List<Signal> signalList = message.getSignals();
                log.trace("message name[{}] and id [{}]", message.getName(), message.getId());
                if (signalList != null) {
                    for (Signal signal : signalList) {
                        log.trace("signal name is {}", signal.getName());
                        // TIPS: DBC中定义的是小写，但是实际在matrix中可能定义大写, 比较的时候不能忽略大小写
                        if (sigName.equals(signal.getName())) {
                            //todo 后续可以检查设置的值是否正确
                            flag = true;
                            canAction.setMessageId(message.getId());
                            break;
                        }
                    }
                }
            }
            if (!flag) {
                String e = getError(SHEET_NAME, index) + "的信号名称[" + sigName + "]在DBC中找不到，请仔细检查";
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void check(List<CanAction> canActions, List<Message> messages) {
        // 检查condition和FunctionName不能重名
        checkCategoriesDuplicateName(canActions, CONDITION);
        checkCategoriesDuplicateName(canActions, ACTION);
        canActions.forEach(canAction -> {
            int index = canAction.getId();
            String category = canAction.getCategory();
            List<Pair<String, String>> dependencies = canAction.getDependency();
            if (category.equalsIgnoreCase(CONDITION)) {
                // 检查不能有依赖
                if (dependencies.size() != 0) {
                    String e = getError(SHEET_NAME, index) + "的依赖有问题，Condition不允许有任何依赖, 当前依赖为";
                    throw new RuntimeException(e);
                }
            } else if (category.equalsIgnoreCase(ACTION)) {
                // 检查依赖的是否正确
                if (dependencies.size() > 0) {
                    // 检查依赖的是否正确
                    checkDependency(canActions, dependencies, index);
                }
            } else {
                String e = getError(SHEET_NAME, index) + "的依赖有问题，只允许[" + CONDITION + "和" + ACTION + "], " +
                        "当前设置的是[" + category + "]";
                throw new RuntimeException(e);
            }
            String functionName = canAction.getFunctionName();
            if (!isFunctionCorrect(functionName)) {
                String e = getError(SHEET_NAME, index) + "的函数名有问题，函数名定义必须符合python规范";
                throw new RuntimeException(e);
            }
            List<Pair<String, String>> signals = canAction.getSignals();
            checkSignals(canAction, signals, messages, index);
        });
    }
}
