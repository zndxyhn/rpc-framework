package org.example.serialize;

import lombok.*;

import java.io.Serializable;

/**
 * @author yuanlin
 * @date 2021/12/30/15:52
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
class Student implements Serializable {
    private String name;
    private int age;
}
