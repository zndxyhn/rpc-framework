package org.example.registry;

import org.example.extension.SPI;
import org.example.remoting.dto.RpcRequest;

import java.net.InetSocketAddress;

/**
 * service discovery
 *
 * @author shuang.kou
 * @createTime 2020年06月01日 15:16:00
 */
@SPI
public interface ServiceDiscovery {
    /**
     * 查找服务地址
     *
     * @param rpcRequest RPC请求
     * @return 服务地址
     */
    InetSocketAddress lookupService(RpcRequest rpcRequest);
}
