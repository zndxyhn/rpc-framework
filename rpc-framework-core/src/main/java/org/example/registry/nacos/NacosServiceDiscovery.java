package org.example.registry.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.example.enums.RpcErrorMessageEnum;
import org.example.exception.RpcException;
import org.example.extension.ExtensionLoader;
import org.example.extension.SPI;
import org.example.loadbalance.LoadBalance;
import org.example.registry.ServiceDiscovery;
import org.example.registry.utils.NacosUtils;
import org.example.remoting.dto.RpcRequest;
import org.apache.commons.collections4.CollectionUtils;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * ClassName: NacosServiceDiscovery
 * Package: org.example.registry.nacos
 * Description:
 *
 * @Author yhn
 * @Create 2024/5/15 17:11
 * @Version 1.0
 */
@Slf4j
public class NacosServiceDiscovery implements ServiceDiscovery {
    private final LoadBalance loadBalancer;

    public NacosServiceDiscovery() {
        this.loadBalancer = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension("loadBalance");
    }

    @Override
    public InetSocketAddress lookupService(RpcRequest rpcRequest) {
        String serviceName = rpcRequest.getRpcServiceName();
        InetSocketAddress address = null;
        try {
            List<String> serviceAddresses = NacosUtils.getAllInstance(serviceName);
            if (CollectionUtils.isEmpty(serviceAddresses)) {
                log.error("can't find service: [{}]", serviceName);
                throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND);
            } else {
//            String targetServiceAddress = loadBalancer.selectServiceAddress(instanceList, rpcRequest);
//            String[] hostAndPort = targetServiceAddress.split(":");
                String targetServiceAddress = serviceAddresses.get(0);
                String[] hostAndPort = targetServiceAddress.split(":");
                String host = hostAndPort[0];
                int port = Integer.parseInt(hostAndPort[1]);
                address = new InetSocketAddress(host, port);
            }
        } catch (NacosException e) {
            log.error("error occured while finding for service: [{}]", serviceName);
        }
        return address;
    }
}
