package org.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * ClassName: RpcResponseCodeEnum
 * Package: org.example.enums
 * Description:
 *
 * @Author yhn
 * @Create 2024/5/14 21:02
 * @Version 1.0
 */
@AllArgsConstructor
@Getter
@ToString
public enum RpcResponseCodeEnum {

    SUCCESS(200, "The remote call is successful"),
    FAIL(500, "The remote call is fail");
    private final int code;

    private final String message;

}
