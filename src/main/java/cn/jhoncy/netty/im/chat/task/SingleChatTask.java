package cn.jhoncy.netty.im.chat.task;

import cn.jhoncy.netty.im.chat.constrants.ChatSession;
import cn.jhoncy.netty.im.chat.entity.SingleChatEntity;
import cn.jhoncy.netty.im.chat.model.ProtocolModel;
import cn.jhoncy.netty.im.utils.JsonUtils;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 单聊任务
 * @author zhuangqi
 * @date 2018-11-17
 */
@Slf4j
public class SingleChatTask implements ChatTask {

    @Override
    public void excute(ChannelHandlerContext ctx, ProtocolModel protocolModel) throws Exception {
        log.info("task[single-chat-task-message] 服务端接收到数据:{}", protocolModel);
        SingleChatEntity singleChatEntity = JsonUtils.fromJson(protocolModel.getBody().getBytes(),SingleChatEntity.class);
        String key = singleChatEntity.getToShellId() + "";
        //发送消息
        ChannelHandlerContext subCtx = ChatSession.sessionMap.get(key);
        if (null != subCtx) {
            if (subCtx.channel().isWritable()) {
                log.info("task[single-chat-task-message]向设备发送数据 ");
                subCtx.pipeline().writeAndFlush(protocolModel);
            } else {
                log.info("task[single-chat-task-message]设备不可写,无法发送数据,关闭连接");
                subCtx.pipeline().close();
            }
        }
    }

}
