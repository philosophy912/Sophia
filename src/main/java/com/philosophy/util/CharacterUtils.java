package com.philosophy.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 23:07
 **/
public class CharacterUtils {
    private Logger log = LogManager.getLogger(CharacterUtils.class);

    /**
     * 去掉windows不支持的字符(用于文件命名)
     *
     * @param source 要去不支持字符的字符串
     * @return 去掉Windows不支持的字符串
     */
    public String setWindowsSupport(String source) {
        char[] windows_not_allowed = {'\\', '/', ':', '*', '?', '"', '<', '>', '|'};
        char[] chars = source.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            boolean flag = false;
            for (char x : windows_not_allowed) {
                if (c == x) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                sb.append(c);
            }
        }
        log.debug("After remove not fit Windows char is " + sb.toString());
        return sb.toString();
    }

    /**
     * 增加前缀数字
     * <br>
     * 如 当前序号为1， 总数为100，则返回001
     *
     * @param index 前缀序号
     * @param total 总共的数量
     * @return 增加前缀后的数字
     */
    public String setPrefixNumber(int index, int total) {
        String str = String.valueOf(index);
        StringBuilder sb = new StringBuilder();
        int size = String.valueOf(total).length();
        for (int i = str.length(); i < size; i++) {
            sb.append("0");
        }
        sb.append(index);
        return sb.toString();
    }


}
