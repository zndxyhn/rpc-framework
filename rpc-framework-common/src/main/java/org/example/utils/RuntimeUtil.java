package org.example.utils;

/**
 * ClassName: RuntimeUtil
 * Package: org.example.utils
 * Description:
 *
 * @Author yhn
 * @Create 2024/5/14 21:00
 * @Version 1.0
 */
public class RuntimeUtil {
    /**
     * 获取CPU的核心数
     *
     * @return cpu的核心数
     */
    public static int cpus() {
        return Runtime.getRuntime().availableProcessors();
    }
}
