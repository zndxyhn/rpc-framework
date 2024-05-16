package org.example.serialize;

import org.example.serialize.json.JsonSerializer;
import org.example.remoting.dto.RpcRequest;
import org.example.remoting.dto.RpcResponse;
import org.example.serialize.hessian.HessianSerializer;
import org.example.serialize.kyro.KryoSerializer;
import org.example.serialize.protostuff.ProtostuffSerializer;
import org.example.utils.JsonUtils;
import org.junit.Test;

import java.util.LinkedHashMap;

/**
 * 测试序列化
 *
 * @author yuanlin
 * @date 2021/12/30/10:46
 */
public class TestSerialization {

    @Test
    public void testHessian() {
        // --------------------- 测试样例 ----------------------- //
        Student student = Student.builder()
                .name("张三")
                .age(18)
                .build();
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName("github.yuanlin.HelloService")
                .group("test")
                .version("01")
                .requestId("123456")
                .methodName("hello")
                .parameters(new Object[]{"1231", "asdsa", 1, 2L})
                .paramTypes(new Class<?>[]{String.class, String.class, Integer.class, Long.class})
                .build();
        RpcResponse rpcResponse = RpcResponse.builder()
                .code(1)
                .message("成功")
                .requestId("123456")
                .data(student)
                .build();
        // --------------------- 测试样例 ----------------------- //

        Serializer serializer = new HessianSerializer();
        byte[] data = serializer.serialize(rpcRequest);
        RpcRequest deserializeRpcReuqest = serializer.deserialize(data, RpcRequest.class);
        System.out.println(deserializeRpcReuqest);

        byte[] data1 = serializer.serialize(rpcResponse);
        RpcResponse deserializeRpcResponse = serializer.deserialize(data1, RpcResponse.class);
        Student studentData = (Student) deserializeRpcResponse.getData();
        System.out.println(deserializeRpcResponse);
        System.out.println(studentData);
    }

    @Test
    public void testJson() {
        // --------------------- 测试样例 ----------------------- //
        Student student = Student.builder()
                .name("张三")
                .age(18)
                .build();
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName("org.example.HelloService")
                .group("test")
                .version("01")
                .requestId("123456")
                .methodName("hello")
                .parameters(new Object[]{"1231", "asdsa", 1, 2L})
                .paramTypes(new Class<?>[]{String.class, String.class, Integer.class, Long.class})
                .build();
        RpcResponse rpcResponse = RpcResponse.builder()
                .code(1)
                .message("成功")
                .requestId("123456")
                .data(student)
                .build();
        // --------------------- 测试样例 ----------------------- //

        Serializer serializer = new JsonSerializer();
        byte[] data = serializer.serialize(rpcRequest);
        Class<?> cls = RpcRequest.class;
        RpcRequest deserializeRpcReuqest = (RpcRequest) serializer.deserialize(data, cls);
        System.out.println(deserializeRpcReuqest);

        byte[] data1 = serializer.serialize(rpcResponse);
        RpcResponse deserializeRpcResponse = serializer.deserialize(data1, RpcResponse.class);
        Student studentData = JsonUtils.mapToObj((LinkedHashMap<String, Object>) deserializeRpcResponse.getData(), Student.class);
        System.out.println(deserializeRpcResponse);
        System.out.println(studentData);
    }

    @Test
    public void testKryo() {
        // --------------------- 测试样例 ----------------------- //
        Student student = Student.builder()
                .name("张三")
                .age(18)
                .build();
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName("github.yuanlin.HelloService")
                .group("test")
                .version("01")
                .requestId("123456")
                .methodName("hello")
                .parameters(new Object[]{"1231", "asdsa", 1, 2L})
                .paramTypes(new Class<?>[]{String.class, String.class, Integer.class, Long.class})
                .build();
        RpcResponse rpcResponse = RpcResponse.builder()
                .code(1)
                .message("成功")
                .requestId("123456")
                .data(student)
                .build();
        // --------------------- 测试样例 ----------------------- //

        Serializer serializer = new KryoSerializer();
        byte[] data = serializer.serialize(rpcRequest);
        RpcRequest deserializeRpcReuqest = serializer.deserialize(data, RpcRequest.class);
        System.out.println(deserializeRpcReuqest);

        byte[] data1 = serializer.serialize(rpcResponse);
        RpcResponse deserializeRpcResponse = serializer.deserialize(data1, RpcResponse.class);
        Student studentData = (Student) deserializeRpcResponse.getData();
        System.out.println(deserializeRpcResponse);
        System.out.println(studentData);
    }

    @Test
    public void testProtostuff() {
        // --------------------- 测试样例 ----------------------- //
        Student student = Student.builder()
                .name("张三")
                .age(18)
                .build();
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName("github.yuanlin.HelloService")
                .group("test")
                .version("01")
                .requestId("123456")
                .methodName("hello")
                .parameters(new Object[]{"1231", "asdsa", 1, 2L})
                .paramTypes(new Class<?>[]{String.class, String.class, Integer.class, Long.class})
                .build();
        RpcResponse rpcResponse = RpcResponse.builder()
                .code(1)
                .message("成功")
                .requestId("123456")
                .data(student)
                .build();
        // --------------------- 测试样例 ----------------------- //

        Serializer serializer = new ProtostuffSerializer();
        byte[] data = serializer.serialize(rpcRequest);
        RpcRequest deserializeRpcReuqest = serializer.deserialize(data, RpcRequest.class);
        System.out.println(deserializeRpcReuqest);

        byte[] data1 = serializer.serialize(rpcResponse);
        RpcResponse deserializeRpcResponse = serializer.deserialize(data1, RpcResponse.class);
        Student studentData = (Student) deserializeRpcResponse.getData();
        System.out.println(deserializeRpcResponse);
        System.out.println(studentData);
    }
}

