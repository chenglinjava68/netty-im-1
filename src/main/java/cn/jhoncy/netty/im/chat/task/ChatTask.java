package cn.jhoncy.netty.im.chat.task;

import cn.jhoncy.netty.im.chat.model.ProtocolModel;
import io.netty.channel.ChannelHandlerContext;

/**
 * 聊天任务接口
 * @author zhuangqi
 * @date 2018-11-17
 */
public interface ChatTask {

    /**
     * 执行任务
     * @param ctx
     * @param protocolModel
     * @throws Exception
     */
    public void excute(ChannelHandlerContext ctx, ProtocolModel protocolModel) throws Exception;


}
