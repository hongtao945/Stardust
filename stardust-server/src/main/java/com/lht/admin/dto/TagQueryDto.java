package com.lht.admin.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author lht
 * @date 2021/3/25 - 19:47
 * @description:
 */
@Data
public class TagQueryDto implements Serializable {
    private String queryName;
    private String startTime;
    private String endTime;
}
