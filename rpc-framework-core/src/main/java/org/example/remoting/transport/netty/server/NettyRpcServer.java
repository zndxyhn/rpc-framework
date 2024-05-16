package org.example.remoting.transport.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.example.annotation.RpcService;
import org.example.config.RpcServiceConfig;
import org.example.factory.SingletonFactory;
import org.example.provider.ServiceProvider;
import org.example.provider.nacos.NacosServiceProviderImpl;
import org.example.remoting.transport.netty.codec.RpcMessageDecoder;
import org.example.remoting.transport.netty.codec.RpcMessageEncoder;
import org.example.remoting.transport.netty.server.hook.ServerShutdownHook;
import org.example.utils.RuntimeUtil;
import org.example.utils.concurrent.threadpool.ThreadPoolFactoryUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: NettyRpcServer
 * Package: org.example.transport.netty.server
 * Description:
 *
 * @Author yhn
 * @Create 2024/5/14 22:00
 * @Version 1.0
 */
@Slf4j
@Component
public class NettyRpcServer implements BeanPostProcessor {

    protected String host = InetAddress.getLoopbackAddress().getHostAddress();
    protected int port = 8002;
    /**
     * 存放扫描到的标注 @RpcService 的服务
     */
    protected Set<RpcServiceConfig> serviceConfigs = new HashSet<>();

    private final ServiceProvider serviceProvider = SingletonFactory.getInstance(NacosServiceProviderImpl.class);

    public NettyRpcServer(){}

    public NettyRpcServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void registerService(RpcServiceConfig rpcServiceConfig) {
        serviceProvider.publishService(rpcServiceConfig, port);
        if (CollectionUtils.isNotEmpty(serviceConfigs)) {
            for (RpcServiceConfig serviceConfig : serviceConfigs) {
                serviceProvider.publishService(serviceConfig, port);
            }
        } else {
            log.warn("@RpcService set is empty");
        }
    }
    /**
     * 扫描标注了 @RpcService 的服务
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        RpcService rpcServiceAno = bean.getClass().getAnnotation(RpcService.class);
        if (rpcServiceAno != null) {
            RpcServiceConfig serviceConfig = RpcServiceConfig.builder()
                    .service(bean)
                    .group(rpcServiceAno.group())
                    .version(rpcServiceAno.version())
                    .build();
            serviceConfigs.add(serviceConfig);
            log.info("find rpc service: [{}]", serviceConfig.getServiceName());
        }
        return bean;
    }
    @SneakyThrows
    public void start(RpcServiceConfig rpcServiceConfig){
        //关闭当前的服务并注销所有服务
        String host = InetAddress.getLocalHost().getHostAddress();
        ServerShutdownHook.getServerShutdownHook().clearAllOnClose(new InetSocketAddress(host, port));
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        DefaultEventExecutorGroup serviceHandlerGroup = new DefaultEventExecutorGroup(
                RuntimeUtil.cpus() * 2,
                ThreadPoolFactoryUtil.createThreadFactory("service-handler-group", false)
        );
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    // TCP默认开启了 Nagle 算法，该算法的作用是尽可能的发送大数据快，减少网络传输。TCP_NODELAY 参数的作用就是控制是否启用 Nagle 算法。
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    // 是否开启 TCP 底层心跳机制
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //表示系统用于临时存放已完成三次握手的请求的队列的最大长度,如果连接建立频繁，服务器处理创建新连接较慢，可以适当调大这个参数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    // 当客户端第一次进行请求的时候才会进行初始化
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 30 秒之内没有收到客户端请求的话就关闭连接
                            pipeline.addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
                            pipeline.addLast(new RpcMessageEncoder());
                            pipeline.addLast(new RpcMessageDecoder());
                            pipeline.addLast(serviceHandlerGroup, new NettyRpcServerHandler());
                        }
                    });
            // 绑定端口，同步等待绑定成功
            ChannelFuture sync = b.bind(host, port).sync();
            log.debug("netty server started on port {}", port);
            // 注册标注了 @RpcService 的服务
            registerService(rpcServiceConfig);
            // 等待服务端监听端口关闭
            sync.channel().closeFuture().sync();
        }catch (InterruptedException e){
            log.error("occur exception when start server:", e);
        }finally {
            log.error("shutdown bossGroup and workerGroup");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            serviceHandlerGroup.shutdownGracefully();
        }

    }

    public  void publishService(RpcServiceConfig config) {
        serviceProvider.publishService(config, port);
    }
}
