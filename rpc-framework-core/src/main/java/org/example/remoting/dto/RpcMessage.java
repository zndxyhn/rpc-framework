package org.example.remoting.dto;

import lombok.*;

/**
 * ClassName: RpcMessage
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
public class RpcMessage {

    /**
     * rpc message type
     */
    private byte messageType;
    /**
     * serialization type
     */
    private byte codec;
    /**
     * compress type
     */
    private byte compress;
    /**
     * request id
     */
    private int requestId;
    /**
     * request data
     */
    private Object data;

}
