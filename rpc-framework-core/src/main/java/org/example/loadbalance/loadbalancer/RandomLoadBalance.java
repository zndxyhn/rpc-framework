package org.example.loadbalance.loadbalancer;

import org.example.loadbalance.AbstractLoadBalance;
import org.example.remoting.dto.RpcRequest;

import java.util.List;
import java.util.Random;

/**
 * ClassName: RandomLoadBalance
 * Package: org.example.loadbalance.loadbalancer
 * Description:
 *  实现随机负载均衡策略
 * @Author yhn
 * @Create 2024/5/15 16:55
 * @Version 1.0
 */
public class RandomLoadBalance extends AbstractLoadBalance {
    @Override
    protected String doSelect(List<String> serviceAddresses, RpcRequest rpcRequest) {
        Random random = new Random();
        return serviceAddresses.get(random.nextInt(serviceAddresses.size()));
    }
}
