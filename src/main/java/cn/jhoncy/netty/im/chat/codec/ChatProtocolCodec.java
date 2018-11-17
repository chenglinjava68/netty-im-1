package cn.jhoncy.netty.im.chat.codec;

import cn.jhoncy.netty.im.chat.model.ProtocolModel;
import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhuangqi
 * @date 2018-11-17
 */
@Component
@ChannelHandler.Sharable
public class ChatProtocolCodec extends MessageToMessageCodec<String, ProtocolModel> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ProtocolModel protocolModel, List<Object> list) throws Exception {
        list.add(JSON.toJSONString(protocolModel));
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, String s, List<Object> list) throws Exception {
        ProtocolModel protocolModel = JSON.parseObject(s,ProtocolModel.class);
        list.add(protocolModel);
    }

}
