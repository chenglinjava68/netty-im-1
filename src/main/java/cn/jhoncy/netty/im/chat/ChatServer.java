package cn.jhoncy.netty.im.chat;

import cn.jhoncy.netty.im.chat.handle.ChatServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhuangqi
 * @date 2018-11-17
 */
@Slf4j
@Component
public class ChatServer {

    @Value("${websocket.port}")
    private Integer port;

    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workGroup = new NioEventLoopGroup();
    private ChannelFuture channelFuture;

    /**
     * 启动
     */
    public void start() {

        long begin = System.currentTimeMillis();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            //boss辅助客户端的tcp连接请求  worker负责与客户端之前的读写操作
            bootstrap.group(bossGroup,workGroup)
                    //配置客户端的channel类型
                    .channel(NioServerSocketChannel.class)
                    //配置TCP参数，握手字符串长度设置
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //TCP_NODELAY算法，尽可能发送大块数据，减少充斥的小块数据
                    .option(ChannelOption.TCP_NODELAY, true)
                    //开启心跳包活机制，就是客户端、服务端建立连接处于ESTABLISHED状态，超过2小时没有交流，机制会被启动
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //配置固定长度接收缓存区分配器
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(592048))
                    .childHandler(new ChatServerHandler());
            long end = System.currentTimeMillis();
            log.info("Netty Websocket服务器启动完成，耗时 " + (end - begin) + " ms，已绑定端口 " + port + " 阻塞式等候客户端连接");
            // 绑定端口，同步等待成功
            bootstrap.bind(port).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //jvm中增加一个关闭的钩子，当jvm关闭的时候，会执行系统中已经设置的所有通过方法addShutdownHook添加的钩子，当系统执行完这些钩子后，jvm才会关闭
            Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
        }
    }

    /**
     * 重启
     * @throws Exception
     */
    public void restart() throws Exception {
        shutdown();
        start();
    }

    /**
     * 关闭
     */
    public void shutdown() {
        if (channelFuture != null) {
            channelFuture.channel().close().syncUninterruptibly();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workGroup != null) {
            workGroup.shutdownGracefully();
        }
    }

}
