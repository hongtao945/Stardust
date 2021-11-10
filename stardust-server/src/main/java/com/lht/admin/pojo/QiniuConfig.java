package com.lht.admin.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 
 * </p>
 *
 * @author lht
 * @since 2021-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_qiniu_config")
@ApiModel(value="QiniuConfig对象", description="")
public class QiniuConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "AccessKey")
    @NotBlank
    private String accessKey;

    @ApiModelProperty(value = "SecretKey")
    @NotBlank
    private String secretKey;

    @ApiModelProperty(value = "空间名称")
    @NotBlank
    private String bucket;

    @ApiModelProperty(value = "域名")
    @NotBlank
    private String domain;

    @ApiModelProperty(value = "类型：公开或私有")
    private String type = "公开";

    @ApiModelProperty(value = "地区")
    @NotBlank
    private String zone;


}
