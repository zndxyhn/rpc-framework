package org.example.netty;


import org.example.config.RpcServiceConfig;
import org.example.netty.service.HelloService;
import org.example.netty.service.HelloServiceImpl;
import org.example.proxy.RpcClientProxy;
import org.example.remoting.transport.netty.client.NettyRpcClient;
import org.example.remoting.transport.netty.server.NettyRpcServer;
import org.junit.Test;

/**
 * 测试使用代理对象进行远程调用
 *
 * @author yuanlin
 * @date 2022/01/12/11:58
 */
public class TestRpcDynamicProxy {

    /**
     * receive msg: [RpcMessage(requestId=1, messageType=1, codec=1, payload=RpcRequest(requestId=78f8031c-4611-414e-8d52-0efeb150784e, interfaceName=github.yuanlin.netty.service.HelloService, methodName=hello, parameterTypes=[class java.lang.String], parameters=[hello test ], version=01, group=test))]
     * server get result: [hello test  world]
     */
    @Test
    public void testRpcDynamicProxy_server() {
        // 1. 创建服务端
        NettyRpcServer server = new NettyRpcServer("127.0.0.1", 8002);
        // 2. 发布服务
        HelloService helloService = new HelloServiceImpl();
        RpcServiceConfig config = RpcServiceConfig.builder()
                .service(helloService)
                .group("test")
                .version("version1")
                .build();
        server.publishService(config);
        // 3. 启动服务端
        server.start(config);
    }

    /**
     * client send message: [RpcMessage(requestId=1, messageType=1, codec=1, payload=RpcRequest(requestId=78f8031c-4611-414e-8d52-0efeb150784e, interfaceName=github.yuanlin.netty.service.HelloService, methodName=hello, parameterTypes=[class java.lang.String], parameters=[hello test ], version=01, group=test))]
     * codec name: [hessian]
     * client receive response: [RpcMessage(requestId=1, messageType=2, codec=1, payload=RpcResponse(requestId=78f8031c-4611-414e-8d52-0efeb150784e, statusCode=200, message=远程调用成功, data=hello test  world))]
     * hello test  world
     */
    @Test
    public void testRpcDynamicProxy_client() {
        // 1. 创建客户端
        NettyRpcClient client = new NettyRpcClient();
        HelloService service = new HelloServiceImpl();
        RpcServiceConfig config = RpcServiceConfig.builder()
                .service(service)
                .group("test")
                .version("version1")
                .build();
        // 2. 创建代理对象
        RpcClientProxy proxy = new RpcClientProxy(client, config);
        System.out.println(proxy.getClass());
        HelloService helloService = proxy.getProxy(HelloService.class);
        // 3. 远程调用
        System.out.println(helloService.hello("hello "));
    }
}
