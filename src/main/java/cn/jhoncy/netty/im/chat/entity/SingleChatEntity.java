package cn.jhoncy.netty.im.chat.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 单聊模型
 * @author zhuangqi
 * @date 2018-11-17
 */
@Data
public class SingleChatEntity {

    @JsonProperty("from_shell_id")
    private Long fromShellId;

    @JsonProperty("to_shell_id")
    private Long toShellId;

    @JsonProperty("content")
    private String content;

}
