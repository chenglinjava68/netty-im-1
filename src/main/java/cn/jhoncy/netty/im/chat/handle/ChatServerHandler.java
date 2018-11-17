package cn.jhoncy.netty.im.chat.handle;

import cn.jhoncy.netty.im.chat.model.ProtocolModel;
import cn.jhoncy.netty.im.chat.task.ChatTask;
import cn.jhoncy.netty.im.chat.task.TaskManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *
 * @author zhuangqi
 * @date 2018-11-17
 */
@Slf4j
@Component
public class ChatServerHandler extends SimpleChannelInboundHandler<ProtocolModel> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtocolModel protocolModel) throws Exception {
        log.info("服务端接收到客户端消息:{}", protocolModel);
        Integer commandId = protocolModel.getCommandId();
        if (null != commandId) {
            ChatTask chatTask = TaskManager.getInstance().getTask(commandId);
            if (null != chatTask) {
                chatTask.excute(ctx,protocolModel);
            } else {
                log.error("not found chatTask from commandId:{}",commandId);
            }
        } else {
            log.error("not found commandId");
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("链路激活");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("服务端监听到客户端退出, 进行业务处理");
        // TODO 移除channel
        log.info("服务端监听到客户端退出, 业务处理成功");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("exceptionCaught", cause);
    }
}

