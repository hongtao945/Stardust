package com.lht.admin.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author lht
 * @date 2021/3/20 - 22:56
 * @description: 构建树的实体类
 */
@Data
public class MenuDto implements Serializable {

    private Long id;

    private Long parentId;

    /**
     * 是否被选中，默认0：未被选中
     */
    private String checkArr = "0";

    private String title;
}
