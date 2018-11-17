package cn.jhoncy.netty.im.chat.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作类型枚举
 * @author zhuangqi
 * @date 2018-11-17
 */
@Getter
@AllArgsConstructor
public enum CommandEnum {
    /** 发送单聊信息 **/
    SINGLE_SEND(1);
    private Integer value;
}
