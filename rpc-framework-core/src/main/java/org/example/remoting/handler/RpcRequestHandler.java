package org.example.remoting.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.RpcException;
import org.example.factory.SingletonFactory;
import org.example.provider.ServiceProvider;
import org.example.provider.nacos.NacosServiceProviderImpl;
import org.example.remoting.dto.RpcRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ClassName: RpcRequestHandler
 * Package: org.example.remoting.handler
 * Description:
 *
 * @Author yhn
 * @Create 2024/5/15 14:42
 * @Version 1.0
 */
@Slf4j
public class RpcRequestHandler {
    private final ServiceProvider serviceProvider;

    //Todo:改为Nacos
    public RpcRequestHandler() {
        serviceProvider = SingletonFactory.getInstance(NacosServiceProviderImpl.class);
    }

    /**
     * 处理rpcRequest:调用相应的方法，然后返回该方法
     * @param rpcRequest
     * @return
     */
    public Object handle(RpcRequest rpcRequest){
        Object service = serviceProvider.getService(rpcRequest.getRpcServiceName());
        return invokeTargetMethod(rpcRequest, service);
    }

    /**
     *  获取方法执行结果
     * @param rpcRequest
     * @param service
     * @return
     */
    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service){
        Object result;
        try {
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            result = method.invoke(service, rpcRequest.getParameters());
            log.info("service:[{}] successful invoke method:[{}]", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());
        } catch (NoSuchMethodException | IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
            throw new RpcException(e.getMessage(), e);
        }
        return result;
    }


}
