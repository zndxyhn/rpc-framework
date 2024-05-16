package org.example.registry.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.example.enums.RpcConfigEnum;
import org.example.enums.RpcErrorMessageEnum;
import org.example.exception.RpcException;
import org.example.registry.ServiceRegistry;
import org.example.registry.utils.NacosUtils;
import org.example.utils.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Properties;

/**
 * ClassName: NacosServiceRegistryImpl
 * Package: org.example.registry.nacos
 * Description:
 *
 * @Author yhn
 * @Create 2024/5/15 16:11
 * @Version 1.0
 */
@Slf4j
public class NacosServiceRegistry implements ServiceRegistry {
    @Override
    public void registerService(String rpcServiceName, InetSocketAddress inetSocketAddress) {
        try {
            NacosUtils.registerService(rpcServiceName, inetSocketAddress);
        } catch (NacosException e) {
            log.error("注册服务时有错误发生:", e);
            throw new RpcException(RpcErrorMessageEnum.REGISTER_SERVICE_FAILED);
        }

    }

}
