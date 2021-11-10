package com.lht.admin.vo;

import com.lht.admin.pojo.Notice;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author lht
 * @date 2021/11/3 - 21:22
 * @description: 个人信息页面视图对象
 */
@Data
@ApiModel(value = "个人信息页面视图对象")
public class PersonPageVo {

    @ApiModelProperty(value = "最近的公告")
    private List<Notice> notices;
}
