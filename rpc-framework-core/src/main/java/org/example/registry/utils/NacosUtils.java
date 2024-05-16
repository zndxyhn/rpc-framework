package org.example.registry.utils;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.example.enums.RpcConfigEnum;
import org.example.enums.RpcErrorMessageEnum;
import org.example.exception.RpcException;
import org.example.utils.PropertiesUtils;

import java.net.InetSocketAddress;
import java.util.*;

/**
 * ClassName: NacosUtils
 * Package: org.example.registry.utils
 * Description:
 *  操作Nacos的工具类
 * @Author yhn
 * @Create 2024/5/15 17:13
 * @Version 1.0
 */
@Slf4j
public class NacosUtils {
    private static String SERVER_ADDR = "127.0.0.1:8848";
    private static final NamingService namingService;

    private static final Set<String> serviceNames = new HashSet<>();

    static {
        try {
            namingService = NamingFactory.createNamingService(SERVER_ADDR);
            Properties properties = PropertiesUtils.readPropertiesFile(RpcConfigEnum.RPC_CONFIG_PATH.getPropertyValue());
            if (properties != null) {
                String defaultNacosAddress = properties.getProperty(RpcConfigEnum.NACOS_ADDRESS.getPropertyValue());
                if (StringUtils.isNotEmpty(defaultNacosAddress)) {
                    SERVER_ADDR = defaultNacosAddress;
                }
            }
        } catch (NacosException e) {
            log.error("连接到Nacos时有错误发生: ", e);
            throw new RpcException(RpcErrorMessageEnum.FAILED_TO_CONNECT_TO_SERVICE_REGISTRY);
        }
    }
    private static NamingService getNacosNamingService() {
        try {
            return NamingFactory.createNamingService(SERVER_ADDR);
        } catch (NacosException e) {
            log.error("connect to nacos [{}] fail", SERVER_ADDR);
            throw new RpcException(RpcErrorMessageEnum.FAILED_TO_CONNECT_TO_SERVICE_REGISTRY);
        }
    }
    /**
     * 根据服务名称和地址注册服务
     * @param serviceName 服务名称
     * @param address 服务地址
     * @throws NacosException
     */
    public static void registerService(String serviceName, InetSocketAddress address) throws NacosException {
        namingService.registerInstance(serviceName, address.getHostName(), address.getPort());
        serviceNames.add(serviceName);
    }

    /**
     * 根绝服务名称获取服务的所有实例
     * @param serviceName 服务名称
     * @return 服务实例集合
     * @throws NacosException
     */
    public static List<String> getAllInstance(String serviceName) throws NacosException {
        List<Instance> allInstances = namingService.getAllInstances(serviceName);
        List<String> instancesStringValue = new ArrayList<>();
        for (Instance instance : allInstances) {
            instancesStringValue.add(instance.getIp() + ":" + instance.getPort());
        }
        return instancesStringValue;
    }

    /**
     * 根据服务地址清理 Nacos
     * @param address 服务地址
     */
    public static void clearRegistry(InetSocketAddress address) {
        if (!serviceNames.isEmpty()) {
            String host = address.getHostName();
            int port = address.getPort();
            for (String serviceName : serviceNames) {
                try {
                    namingService.deregisterInstance(serviceName, host, port);
                } catch (NacosException e) {
                    log.error("clear registry for service [{}] fail", serviceName, e);
                }
            }
            log.info("All registered services on the server are cleared: [{}]", serviceNames);
        }
    }
}
