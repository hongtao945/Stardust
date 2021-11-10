package com.lht.admin.service;

import com.lht.admin.dto.QiniuQueryDto;
import com.lht.admin.pojo.QiniuConfig;
import com.lht.admin.pojo.QiniuContent;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.pojo.ResponseBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lht
 * @since 2021-03-31
 */
public interface IQiniuContentService extends IService<QiniuContent> {

    /**
     * 同步七牛云图床中的图片数据到数据库中
     * @param qiniuConfig
     * @return
     */
    ResponseBean sync(QiniuConfig qiniuConfig);

    /**
     * 模糊查询七牛云中的图片数据
     * @param page
     * @param limit
     * @param qiniuQueryDto
     * @return
     */
    RespondPageBean getPhotoContent(Integer page, Integer limit, QiniuQueryDto qiniuQueryDto);

    /**
     * 批量删除文件
     * @param idList
     * @param qiniuConfig
     * @return
     */
    boolean batchRemoveFile(List<Integer> idList, QiniuConfig qiniuConfig);

    /**
     * 上传文件
     * @param file
     * @param qiniuConfig
     * @return
     */
    ResponseBean upload(MultipartFile file, QiniuConfig qiniuConfig);
}
