package org.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * ClassName: RpcConfigEnum
 * Package: org.example.enums
 * Description:
 *
 * @Author yhn
 * @Create 2024/5/14 21:02
 * @Version 1.0
 */
@AllArgsConstructor
@Getter
public enum RpcConfigEnum {

    RPC_CONFIG_PATH("rpc.properties"),
    NACOS_ADDRESS("rpc.nacos.address"),
    ZK_ADDRESS("rpc.zookeeper.address");

    private final String propertyValue;

}
