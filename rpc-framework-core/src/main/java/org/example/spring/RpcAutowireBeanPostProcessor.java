package org.example.spring;

import org.example.annotation.RpcAutowire;
import org.example.config.RpcServiceConfig;
import org.example.factory.SingletonFactory;
import org.example.proxy.RpcClientProxy;
import org.example.remoting.transport.RpcRequestTransport;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * ClassName: RpcAutowireBeanPostProcessor
 * Package: org.example.spring
 * Description:
 *  给 @RpcAutowire 标注的属性返回代理对象（用于客户端）
 * @Author yhn
 * @Create 2024/5/15 18:36
 * @Version 1.0
 */
@Component
public class RpcAutowireBeanPostProcessor implements BeanPostProcessor {
    private final RpcRequestTransport rpcClient;

    public RpcAutowireBeanPostProcessor() {
        rpcClient = SingletonFactory.getInstance(RpcRequestTransport.class);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)  {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            RpcAutowire rpcAutowire = field.getAnnotation(RpcAutowire.class);
            if (rpcAutowire != null) {
                Class<?> interfaceClass = field.getType();
                RpcServiceConfig serviceConfig = RpcServiceConfig.builder()
                        .service(bean)
                        .group(rpcAutowire.group())
                        .version(rpcAutowire.version())
                        .build();
                RpcClientProxy proxy = new RpcClientProxy(rpcClient, serviceConfig);
                Object proxyObj = proxy.getProxy(interfaceClass);
                field.setAccessible(true);
                try {
                    field.set(bean, proxyObj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }

}
