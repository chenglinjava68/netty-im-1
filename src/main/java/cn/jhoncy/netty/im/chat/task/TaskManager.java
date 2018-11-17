package cn.jhoncy.netty.im.chat.task;

import cn.jhoncy.netty.im.chat.enums.CommandEnum;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 任务管理类
 * @author zhuangqi
 * @date 2018-11-17
 */
@Component
public class TaskManager {

    /**
     * 初始化一次，无须每次要到时候去创建对象
     */
    private Map<Integer,ChatTask> taskMap = new HashMap<>();

    private static TaskManager instance;

    public static TaskManager getInstance() {
        return instance;
    }


    @PostConstruct
    private void init() {
        taskMap.put(CommandEnum.SINGLE_SEND.getValue(), new SingleChatTask());
    }

    /**
     * 获取任务
     * @param commandId
     */
    public ChatTask getTask(Integer commandId) {
        return taskMap.get(commandId);
    }



}
