package org.example.exception;

import org.example.enums.RpcErrorMessageEnum;

/**
 * ClassName: RpcException
 * Package: org.example.exception
 * Description:
 *
 * @Author yhn
 * @Create 2024/5/14 21:00
 * @Version 1.0
 */
public class RpcException extends RuntimeException {
    public RpcException(RpcErrorMessageEnum rpcErrorMessageEnum, String detail) {
        super(rpcErrorMessageEnum.getMessage() + ":" + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcErrorMessageEnum rpcErrorMessageEnum) {
        super(rpcErrorMessageEnum.getMessage());
    }
}
