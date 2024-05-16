package org.example.utils;

/**
 * ClassName: StringUtil
 * Package: org.example.utils
 * Description:
 *  String 工具类
 * @Author yhn
 * @Create 2024/5/14 21:00
 * @Version 1.0
 */
public class StringUtil {

    public static boolean isBlank(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        for (int i = 0; i < s.length(); ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
