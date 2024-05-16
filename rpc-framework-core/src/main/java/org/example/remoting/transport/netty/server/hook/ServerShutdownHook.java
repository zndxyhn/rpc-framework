package org.example.remoting.transport.netty.server.hook;

import lombok.extern.slf4j.Slf4j;
import org.example.registry.utils.CuratorUtils;
import org.example.registry.utils.NacosUtils;

import java.net.InetSocketAddress;

/**
 * ClassName: ServerShutdownHook
 * Package: org.example.remoting.transport.netty.server.hook
 * Description:
 *  服务器关闭前，清理已经注册的服务
 * @Author yhn
 * @Create 2024/5/15 18:15
 * @Version 1.0
 */
@Slf4j
public class ServerShutdownHook {
    private static final ServerShutdownHook SHUTDOWN_HOOK = new ServerShutdownHook();

    public static ServerShutdownHook getServerShutdownHook() {
        return SHUTDOWN_HOOK;
    }

    public void clearAllOnClose(InetSocketAddress seriveAddress) {
        log.info("add shutdownHook to clearService");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            NacosUtils.clearRegistry(seriveAddress);
//            CuratorUtils.clearRegistry(CuratorUtils.getZkClient(), seriveAddress);
        }));
    }
}
