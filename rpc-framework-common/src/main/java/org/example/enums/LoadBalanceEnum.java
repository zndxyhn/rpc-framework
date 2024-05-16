package org.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ClassName: LoadBalanceEnum
 * Package: org.example.enums
 * Description:
 *
 * @Author yhn
 * @Create 2024/5/14 21:02
 * @Version 1.0
 */
@AllArgsConstructor
@Getter
public enum LoadBalanceEnum {

    LOADBALANCE("loadBalance");

    private final String name;
}
