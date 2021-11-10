package com.lht.admin.service.impl;

import com.lht.admin.pojo.QiniuConfig;
import com.lht.admin.mapper.QiniuConfigMapper;
import com.lht.admin.pojo.ResponseBean;
import com.lht.admin.service.IQiniuConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lht.constant.Constant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lht
 * @since 2021-03-31
 */
@Service
public class QiniuConfigServiceImpl extends ServiceImpl<QiniuConfigMapper, QiniuConfig> implements IQiniuConfigService {

    @Resource
    private QiniuConfigMapper qiniuConfigMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseBean addConfig(QiniuConfig qiniuConfig) {
        if (qiniuConfig.getDomain().toLowerCase().startsWith(Constant.HTTP) || qiniuConfig.getDomain().toLowerCase().startsWith(Constant.HTTPS)) {
            // 先查明数据库中是否已有配置 没有的话先进行添加 有的话则进行更新
            if (qiniuConfigMapper.selectById(1) != null) {
                // 更新操作
                qiniuConfig.setId(1);
                if (qiniuConfigMapper.updateById(qiniuConfig) != 0) {
                    return ResponseBean.success("配置更新成功！");
                }return ResponseBean.error("配置更新失败！");
            }else {
                // 数据库中无数据，先进行添加
                if (qiniuConfigMapper.insert(qiniuConfig) != 0) {
                    return ResponseBean.success("配置添加成功！");
                }return ResponseBean.error("配置添加失败！");
            }
        }
        return ResponseBean.error("域名需得是 http:// 或 https:// 开头");
    }
}
