package org.example.remoting.dto;

import lombok.*;

import java.io.Serializable;

/**
 * ClassName: RpcRequest
 * Package: org.example.remoting.dto
 * Description:
 *
 * @Author yhn
 * @Create 2024/5/14 20:59
 * @Version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;
    private String requestId;
    private String interfaceName;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] paramTypes;
    private String version;
    private String group;

    public String getRpcServiceName() {
        return this.getInterfaceName() + this.getGroup() + this.getVersion();
    }
}
