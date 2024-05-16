package org.example.registry;

import org.example.extension.SPI;
import org.example.remoting.dto.RpcRequest;

import java.net.InetSocketAddress;

/**
 * service registration
 *
 * @author shuang.kou
 * @createTime 2020年05月13日 08:39:00
 */
@SPI
public interface ServiceRegistry {
    /**
     * 服务注册
     *
     * @param rpcServiceName    服务名称
     * @param inetSocketAddress 服务地址
     */
    void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress);

}
