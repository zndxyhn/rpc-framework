package org.example.remoting.transport;

import org.example.extension.SPI;
import org.example.remoting.dto.RpcRequest;

/**
 * ClassName: RpcRequestTransport
 * Package: org.example.transport
 * Description:
 *
 * @Author yhn
 * @Create 2024/5/14 21:16
 * @Version 1.0
 */
@SPI
public interface RpcRequestTransport {
    /**
     * 发送和接收RPC请求
     *
     * @param rpcRequest message body
     * @return data from server
     */
    Object sendRpcRequest(RpcRequest rpcRequest);
}
