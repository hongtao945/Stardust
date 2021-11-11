package com.lht.admin.service;

import com.lht.admin.pojo.QiniuConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lht.admin.pojo.ResponseBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lht
 * @since 2021-03-31
 */
public interface IQiniuConfigService extends IService<QiniuConfig> {

    /**
     * 添加或更新七牛云的配置
     * @param qiniuConfig
     * @return
     */
    ResponseBean addConfig(QiniuConfig qiniuConfig);
}
