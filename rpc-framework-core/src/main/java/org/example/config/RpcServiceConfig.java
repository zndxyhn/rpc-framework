package org.example.config;

import lombok.*;

/**
 * ClassName: ServiceConfig
 * Package: org.example.config
 * Description:
 *  RPC 服务配置
 * @Author yhn
 * @Create 2024/5/15 16:54
 * @Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcServiceConfig {
    /**
     * service version
     */
    private String version = "";
    /**
     * when the interface has multiple implementation classes, distinguish by group
     */
    private String group = "";

    /**
     * target service
     */
    private Object service;

    public String getRpcServiceName() {
        return this.getServiceName() + this.getGroup() + this.getVersion();
    }

    public String getServiceName() {
        return this.service.getClass().getInterfaces()[0].getCanonicalName();
    }
}
