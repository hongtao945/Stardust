package com.lht.admin.dto;

import lombok.Data;

/**
 * @Author lht
 * @date 2021/10/22 - 17:25
 * @description: 评论传输对象
 */
@Data
public class CommentDto {
    private String content;
    private String nickname;
    private String email;
    private String link;
}
