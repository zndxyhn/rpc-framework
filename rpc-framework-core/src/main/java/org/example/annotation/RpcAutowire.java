package org.example.annotation;

import java.lang.annotation.*;

/**
 * ClassName: RpcAutowire
 * Package: org.example.annotation
 * Description:
 *  RPC 服务自动注入注解（标注在属性上）
 * @Author yhn
 * @Create 2024/5/15 16:51
 * @Version 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RpcAutowire {

    String version() default "";

    String group() default "";
}
