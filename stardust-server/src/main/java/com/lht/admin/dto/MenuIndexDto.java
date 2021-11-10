package com.lht.admin.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author lht
 * @date 2021/3/22 - 22:33
 * @description:
 */
@Data
public class MenuIndexDto implements Serializable {

    private Integer id;

    private Integer parentId;

    private String title;

    private String icon;

    private Integer type;

    private String href;

    private String permission;

    private List<MenuIndexDto> children;
}
