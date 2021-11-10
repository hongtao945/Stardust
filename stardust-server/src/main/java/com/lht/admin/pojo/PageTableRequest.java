package com.lht.admin.pojo;

import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author lht
 * @date 2021/3/21 - 20:38
 * @description: 分页对象
 */
@Data
public class PageTableRequest implements Serializable {

    private Integer page;
    private Integer limit;

}
