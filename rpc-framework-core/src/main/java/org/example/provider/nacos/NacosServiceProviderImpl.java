package org.example.provider.nacos;

import lombok.extern.slf4j.Slf4j;
import org.example.config.RpcServiceConfig;
import org.example.enums.RpcErrorMessageEnum;
import org.example.enums.ServiceRegistryEnum;
import org.example.exception.RpcException;
import org.example.extension.ExtensionLoader;
import org.example.provider.ServiceProvider;
import org.example.registry.ServiceRegistry;
import org.example.remoting.transport.netty.server.NettyRpcServer;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClassName: NacosServiceProviderImpl
 * Package: org.example.provider.impl
 * Description:
 *
 * @Author yhn
 * @Create 2024/5/15 16:41
 * @Version 1.0
 */
@Slf4j
public class NacosServiceProviderImpl implements ServiceProvider {
    /**
     * key: rpc service name(interface name + version + group)
     * value: service object
     */
    private final Map<String, Object> registeredService;
    private final ServiceRegistry serviceRegistry;

    public NacosServiceProviderImpl() {
        registeredService = new ConcurrentHashMap<>();
        serviceRegistry = ExtensionLoader.getExtensionLoader(ServiceRegistry.class).getExtension(ServiceRegistryEnum.Nacos.getName());
    }

    @Override
    public void addService(RpcServiceConfig rpcServiceConfig) {
        String rpcServiceName = rpcServiceConfig.getRpcServiceName();
        if (registeredService.containsKey(rpcServiceName)) {
            return;
        }
        registeredService.put(rpcServiceName, rpcServiceConfig.getService());
        log.info("Add service: {} and interfaces:{}", rpcServiceName, rpcServiceConfig.getService().getClass().getInterfaces());
    }

    @Override
    public Object getService(String rpcServiceName) {
        Object service = registeredService.get(rpcServiceName);
        if (null == service) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND);
        }
        return service;
    }

    @Override
    public void publishService(RpcServiceConfig rpcServiceConfig, int port) {
        try {
            String host = InetAddress.getLocalHost().getHostAddress();
            this.addService(rpcServiceConfig);
            serviceRegistry.registerService(rpcServiceConfig.getRpcServiceName(), new InetSocketAddress(host, port));
        } catch (UnknownHostException e) {
            log.error("occur exception when getHostAddress", e);
        }
    }
}
