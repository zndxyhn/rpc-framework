package org.example.loadbalance.loadbalancer;

import org.example.loadbalance.AbstractLoadBalance;
import org.example.remoting.dto.RpcRequest;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ClassName: RoundRobinLoadBalancer
 * Package: org.example.loadbalance.loadbalancer
 * Description:
 *
 * @Author yhn
 * @Create 2024/5/15 16:55
 * @Version 1.0
 */
public class RoundRobinLoadBalancer extends AbstractLoadBalance {

    private final AtomicInteger nextServerCyclicCounter = new AtomicInteger(-1);

    @Override
    protected String doSelect(List<String> serviceAddresses, RpcRequest rpcRequest) {
        int nextIdx = incrementAndGetIdx(serviceAddresses.size());
        return serviceAddresses.get(nextIdx);
    }

    private int incrementAndGetIdx(int size) {
        for (; ; ) {
            int current = nextServerCyclicCounter.get();
            int next = (current + 1) % size;
            if (nextServerCyclicCounter.compareAndSet(current, next))
                return next;
        }

    }
}
