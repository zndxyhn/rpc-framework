package org.example.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * ClassName: RpcService
 * Package: org.example.annotation
 * Description:
 * RPC 服务注解（标注在 RPC 服务实现类上）
 * @Author yhn
 * @Create 2024/5/15 16:52
 * @Version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
@Inherited
public @interface RpcService {

    String version() default "";

    String group() default "";
}
