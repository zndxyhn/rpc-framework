package org.example.netty;


import org.example.config.RpcServiceConfig;
import org.example.netty.service.HelloService;
import org.example.netty.service.HelloServiceImpl;
import org.example.remoting.dto.RpcRequest;
import org.example.remoting.dto.RpcResponse;
import org.example.remoting.transport.netty.client.NettyRpcClient;
import org.example.remoting.transport.netty.server.NettyRpcServer;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 测试Netty客户端和服务器之间能够正确传输数据
 * 测试方法（测试前确保 ZooKeeper(port : 2181) 或 Nacos(port : 8848) 在本地启动）:
 *      1.调整 AbstractServiceProvider 和 NettyClient 中的注册中心实现
 *      2.调整 NettyClient 中发送消息的序列化方式
 * 测试样例:
 *      1.注册中心 ZooKeeper + 序列化 Protostuff
 *      2.注册中心 Nacos + 序列化 Protostuff
 *      3.注册中心 ZooKeeper + 序列化 Kryo
 *      4.注册中心 Nacos + 序列化 Kryo
 *      5.注册中心 ZooKeeper + 序列化 Hessian
 *      6.注册中心 Nacos + 序列化 Hessian
 *      7.测试异常情况，服务端未启动，未找到指定服务，不识别的序列化方式，不支持协议版本，协议包无法识别...
 *
 * 注: 先启动服务端，再使用客户端连接
 *
 * @author yuanlin
 * @date 2022/01/08/14:16
 */
public class TestNettyTransport {

    /**
     * receive msg: [RpcMessage(requestId=0, messageType=1, codec=4, payload=RpcRequest(requestId=123456, interfaceName=github.yuanlin.netty.service.HelloService, methodName=hello, parameterTypes=[class java.lang.String], parameters=[hello], version=01, group=test))]
     */
    @Test
    public void testNettyServer() throws ExecutionException, InterruptedException {
        // 1. 创建服务端
        NettyRpcServer server = new NettyRpcServer("127.0.0.1", 8002);
        // 2. 发布服务
        HelloService helloService = new HelloServiceImpl();
        RpcServiceConfig config = RpcServiceConfig.builder()
                .service(helloService)
                .group("test")
                .version("01")
                .build();
        server.publishService(config);
        // 3. 启动服务端
        server.start(config);
    }

    /**
     * client send message: [RpcMessage(requestId=0, messageType=1, codec=4, payload=RpcRequest(requestId=123456, interfaceName=github.yuanlin.netty.service.HelloService, methodName=hello, parameterTypes=[class java.lang.String], parameters=[hello], version=01, group=test))]
     */
    @Test
    public void testNettyClient() throws ExecutionException, InterruptedException {
        // 1. 创建客户端
        NettyRpcClient client = new NettyRpcClient();
        // 2. 发送RPC请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .requestId("123456")
                .interfaceName("org.example.netty.service.HelloService")
                .methodName("hello")
                .paramTypes(new Class[]{String.class})
                .parameters(new Object[]{"hello"})
                .group("test")
                .version("01")
                .build();
        // 3. 阻塞等待服务端响应
        CompletableFuture<RpcResponse<Object>> f = (CompletableFuture<RpcResponse<Object>>) client.sendRpcRequest(rpcRequest);
        f.get();
    }
}
