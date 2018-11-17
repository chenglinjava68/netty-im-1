package cn.jhoncy.netty.im.chat.constrants;

import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author zhuangqi
 * @date 2018-11-17
 */
public class ChatSession {

    public static Map<String, ChannelHandlerContext> sessionMap = new ConcurrentHashMap<>();

}
