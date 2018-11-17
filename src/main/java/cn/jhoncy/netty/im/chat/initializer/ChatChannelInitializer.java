package cn.jhoncy.netty.im.chat.initializer;

import cn.jhoncy.netty.im.chat.codec.ChatProtocolCodec;
import cn.jhoncy.netty.im.chat.handle.ChatServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhuangqi
 * @date 2018-11-17
 */
@Component
public class ChatChannelInitializer extends ChannelInitializer<NioSocketChannel> {

    @Autowired
    private ChatServerHandler chatServerHandler;
    @Autowired
    private ChatProtocolCodec chatProtocolCodec;

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // HTTP编码解码器
        pipeline.addLast("http-codec", new HttpServerCodec());
        // 保证接收的 Http请求的完整性
        pipeline.addLast("aggregator",new HttpObjectAggregator(64 * 1024));
        // 支持一步发送大的码流，但不占用过多但内存
        pipeline.addLast("http-chunked", new ChunkedWriteHandler());
        // 处理其他的 WebSocketFrame
        pipeline.addLast(new WebSocketServerProtocolHandler("/chat"));
        // 处理 TextWebSocketFrame
        pipeline.addLast(chatProtocolCodec);
        pipeline.addLast(chatServerHandler);
    }


}
