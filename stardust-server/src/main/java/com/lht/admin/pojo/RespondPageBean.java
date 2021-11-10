package com.lht.admin.pojo;

import com.lht.utils.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author lht
 * @date 2021/3/21 - 20:51
 * @description: 表格数据返回的对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespondPageBean implements Serializable {


    private Integer code;

    /**
     * 数据总条数,变量名需得是count，否则layui会无法识别导致数据总条数渲染失败
     */
    private Long count;

    /**
     * 数据本体
     */
    private List<?> data;

    public RespondPageBean(Long count, List<?>data) {
        this.code = ResultCode.TABLE_SUCCESS;
        this.count = count;
        this.data = data;
    }

}
