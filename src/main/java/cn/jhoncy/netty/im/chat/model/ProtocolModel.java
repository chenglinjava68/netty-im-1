package cn.jhoncy.netty.im.chat.model;

import lombok.Data;

/**
 * @author zhuangqi
 * @date 2018-11-17
 */
@Data
public class ProtocolModel {

    /**
     * 版本
     */
    private short version;

    /**
     * 操作类型
     */
    private Integer commandId;

    /**
     * 设备id
     */
    private long shellId;

    /**
     * 协议包体
     */
    private String body;

}
