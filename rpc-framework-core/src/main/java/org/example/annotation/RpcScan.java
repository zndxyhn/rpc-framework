package org.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName: RpcScan
 * Package: org.example.annotation
 * Description:
 * RPC 服务扫描包（标注在启动类上）
 * @Author yhn
 * @Create 2024/5/15 16:51
 * @Version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcScan {

    String[] basePackages();
}
