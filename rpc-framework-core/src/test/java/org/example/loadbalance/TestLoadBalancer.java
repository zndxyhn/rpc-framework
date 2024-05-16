package org.example.loadbalance;

import com.alibaba.nacos.shaded.io.grpc.LoadBalancer;

import org.example.config.RpcServiceConfig;
import org.example.loadbalance.loadbalancer.RoundRobinLoadBalancer;
import org.example.loadbalance.loadbalancer.RandomLoadBalance;
import org.example.loadbalance.loadbalancer.ConsistentHashLoadBalance;
import org.example.netty.service.HelloService;
import org.example.netty.service.HelloServiceImpl;
import org.example.remoting.dto.RpcRequest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 测试负载均衡
 *
 * @author yuanlin
 * @date 2022/01/12/15:39
 */
public class TestLoadBalancer {

    @Test
    public void testRoundRobinLoadBalancer() {
        AbstractLoadBalance balancer = new RoundRobinLoadBalancer();
        // 服务地址
        List<String> serviceUrlList = new ArrayList<>(Arrays.asList("127.0.0.1:8001", "127.0.0.1:8002", "127.0.0.1:8003"));
        HelloService helloService = new HelloServiceImpl();
        RpcServiceConfig config = RpcServiceConfig.builder()
                .service(helloService)
                .group("test")
                .version("01")
                .build();
        RpcRequest rpcRequest = RpcRequest.builder()
                .requestId("123456")
                .interfaceName(config.getServiceName())
                .methodName("hello")
                .paramTypes(new Class[]{String.class})
                .parameters(new Object[]{"hello"})
                .group("test")
                .version("01")
                .build();
        System.out.println("第一次: " + balancer.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println("第二次: " + balancer.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println("第三次: " + balancer.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println("第四次: " + balancer.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println("第五次: " + balancer.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println("第六次: " + balancer.selectServiceAddress(serviceUrlList, rpcRequest));
    }

    @Test
    public void testRandomLoadBalancer() {
        AbstractLoadBalance balancer = new RandomLoadBalance();
        // 服务地址
        List<String> serviceUrlList = new ArrayList<>(Arrays.asList("127.0.0.1:8001", "127.0.0.1:8002", "127.0.0.1:8003"));
        HelloService helloService = new HelloServiceImpl();
        RpcServiceConfig config = RpcServiceConfig.builder()
                .service(helloService)
                .group("test")
                .version("01")
                .build();
        RpcRequest rpcRequest = RpcRequest.builder()
                .requestId("123456")
                .interfaceName(config.getServiceName())
                .methodName("hello")
                .paramTypes(new Class[]{String.class})
                .parameters(new Object[]{"hello"})
                .group("test")
                .version("01")
                .build();
        System.out.println("第一次: " + balancer.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println("第二次: " + balancer.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println("第三次: " + balancer.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println("第四次: " + balancer.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println("第五次: " + balancer.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println("第六次: " + balancer.selectServiceAddress(serviceUrlList, rpcRequest));
    }

    @Test
    public void testConsistentHashLoadBalancer() {
        AbstractLoadBalance balancer = new ConsistentHashLoadBalance();
        // 服务地址
        List<String> serviceUrlList = new ArrayList<>(Arrays.asList("127.0.0.1:8001", "127.0.0.1:8002", "127.0.0.1:8003"));
        HelloService helloService = new HelloServiceImpl();
        RpcServiceConfig config = RpcServiceConfig.builder()
                .service(helloService)
                .group("test")
                .version("01")
                .build();
        RpcRequest rpcRequest = RpcRequest.builder()
                .requestId("123456")
                .interfaceName(config.getServiceName())
                .methodName("hello")
                .paramTypes(new Class[]{String.class})
                .parameters(new Object[]{"hello222234234234"})
                .group("test")
                .version("01")
                .build();
        System.out.println("第一次: " + balancer.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println("第二次: " + balancer.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println("第三次: " + balancer.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println("第四次: " + balancer.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println("第五次: " + balancer.selectServiceAddress(serviceUrlList, rpcRequest));
        System.out.println("第六次: " + balancer.selectServiceAddress(serviceUrlList, rpcRequest));
//        ConsistentHashLoadBalancer.ConsistentHashSelector selector = balancer.selectors.get(rpcRequest.getServiceName());
//        String serviceName = rpcRequest.getServiceName();
//        System.out.println(ConsistentHashLoadBalancer.ConsistentHashSelector.md5(serviceName + Arrays.asList(rpcRequest.getParameters())));
//        System.out.println(serviceName + Arrays.asList(rpcRequest.getParameters()));
//
//        System.out.println(ConsistentHashLoadBalancer.ConsistentHashSelector.md5(serviceName + Arrays.asList(rpcRequest.getParameters())));
//        System.out.println(serviceName + Arrays.asList(rpcRequest.getParameters()));
//
//        System.out.println(ConsistentHashLoadBalancer.ConsistentHashSelector.md5(serviceName + Arrays.asList(rpcRequest.getParameters())));
//        System.out.println(serviceName + Arrays.asList(rpcRequest.getParameters()));
//
//        System.out.println(ConsistentHashLoadBalancer.ConsistentHashSelector.md5(serviceName + Arrays.asList(rpcRequest.getParameters())));
//        System.out.println(serviceName + Arrays.asList(rpcRequest.getParameters()));
    }
}
