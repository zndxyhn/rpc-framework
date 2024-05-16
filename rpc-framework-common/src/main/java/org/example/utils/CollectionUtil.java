package org.example.utils;

import java.util.Collection;

/**
 * ClassName: CollectionUtil
 * Package: org.example.utils
 * Description:
 *   创建 ThreadPool(线程池) 的工具类.
 * @Author yhn
 * @Create 2024/5/14 21:00
 * @Version 1.0
 */
public class CollectionUtil {

    public static boolean isEmpty(Collection<?> c) {
        return c == null || c.isEmpty();
    }

}
