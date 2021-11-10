package com.lht.admin.vo;

import com.lht.admin.pojo.*;
import lombok.Data;

import java.util.List;

/**
 * @Author lht
 * @date 2021/10/28 - 20:27
 * @description: 控制台首页视图对象
 */
@Data
public class ConsoleVo {

    private Integer articleCount;
    private PvAndUvVo pu;
    private Integer messageCount;
    private ViewsDateVo viewsDateVo;
    private List<Notice> notices;
    private List<ConsoleCommentsVo> comments;
    private List<Message> messages;
    private List<Log> logs;

}
