package com.lht.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author lht
 * @date 2021/11/1 - 23:06
 * @description: 后台首页图标视图对象
 */
@Data
@ApiModel(value = "后台首页图标视图对象")
public class ViewsDateVo {

    @ApiModelProperty(value = "日访问量")
    private List<Integer> counts;

    @ApiModelProperty(value = "日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private List<LocalDate> days;
}
