package com.lht.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lht.admin.dto.QiniuQueryDto;
import com.lht.admin.pojo.QiniuConfig;
import com.lht.admin.pojo.QiniuContent;
import com.lht.admin.mapper.QiniuContentMapper;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.pojo.ResponseBean;
import com.lht.admin.service.IQiniuContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lht.exception.BadRequestException;
import com.lht.utils.FileUtil;
import com.lht.utils.JsonUtils;
import com.lht.utils.QiniuUtils;
import com.lht.utils.ResultCode;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lht
 * @since 2021-03-31
 */
@Service
@Slf4j
public class QiniuContentServiceImpl extends ServiceImpl<QiniuContentMapper, QiniuContent> implements IQiniuContentService {
    @Resource
    private QiniuContentMapper qiniuContentMapper;
    @Value("${qiniu.maxsize}")
    private Long maxSize;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseBean sync(QiniuConfig qiniuConfig) {
        if (qiniuConfig == null) {
            return ResponseBean.error("同步失败,请先添加一个七牛云的配置！");
        }
        // 下面的代码到七牛云的官方文档拷贝 https://developer.qiniu.com/kodo/1239/java#7
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(QiniuUtils.getRegionByZone(qiniuConfig.getZone()));
        //...其他参数参考类注释
        String accessKey = qiniuConfig.getAccessKey();
        String secretKey = qiniuConfig.getSecretKey();
        String bucket = qiniuConfig.getBucket();
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //文件名前缀
        String prefix = "";
        //每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 1000;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";
        //列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, prefix, limit, delimiter);
        while (fileListIterator.hasNext()) {
            //处理获取的file list结果
            FileInfo[] items = fileListIterator.next();
            QiniuContent qiniuContent;
            for (FileInfo item : items) {
                // 如果从图床上拿来的数据已经存在于数据库中时就不更新
                if (qiniuContentMapper.selectOne(new QueryWrapper<QiniuContent>().eq("name", FileUtil.getFileNameNoEx(item.key))) != null) {
                    continue;
                }
                // 配置数据信息
                qiniuContent = new QiniuContent();
                qiniuContent.setName(FileUtil.getFileNameNoEx(item.key));
                qiniuContent.setSuffix(FileUtil.getExtensionName(item.key));
                qiniuContent.setBucket(bucket);
                qiniuContent.setType(qiniuConfig.getType());
                qiniuContent.setUpdateTime(LocalDateTime.now());
                qiniuContent.setUrl(qiniuConfig.getDomain() + "/" + item.key);
                qiniuContent.setFileType(FileUtil.getFileType(qiniuContent.getSuffix()));
                qiniuContent.setSize(FileUtil.getSize(Integer.parseInt(item.fsize + "")));
                // 插入数据
                qiniuContentMapper.insert(qiniuContent);
            }
        }
        return ResponseBean.success("同步成功！");
    }

    @Override
    public RespondPageBean getPhotoContent(Integer curPage, Integer limit, QiniuQueryDto qiniuQueryDto) {
        Page<QiniuContent> page = new Page<>(curPage, limit);
        QueryWrapper<QiniuContent> wrapper = new QueryWrapper<>();
        if (qiniuQueryDto.getName() != null) {
            wrapper.like("name", qiniuQueryDto.getName());
        }
        if (qiniuQueryDto.getStartTime() != null && qiniuQueryDto.getEndTime() != null) {
            wrapper.between("update_time", qiniuQueryDto.getStartTime(), qiniuQueryDto.getEndTime());
        }
        IPage<QiniuContent> result = qiniuContentMapper.selectPage(page, wrapper);
        return new RespondPageBean(result.getTotal(), result.getRecords());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchRemoveFile(List<Integer> idList, QiniuConfig qiniuConfig) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(QiniuUtils.getRegionByZone(qiniuConfig.getZone()));
        //...其他参数参考类注释
        String accessKey = qiniuConfig.getAccessKey();
        String secretKey = qiniuConfig.getSecretKey();
        String bucket = qiniuConfig.getBucket();
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        // 遍历idList，进行删除操作
        for (Integer id : idList) {
            QiniuContent qiniuContent = qiniuContentMapper.selectById(id);
            try {
                bucketManager.delete(bucket, qiniuContent.getName() + "." + qiniuContent.getSuffix());
                qiniuContentMapper.deleteById(id);
            } catch (QiniuException ex) {
                //如果遇到异常，说明删除失败
                log.error(ex.code() + " " + ex.response.toString());
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseBean upload(MultipartFile file, QiniuConfig qiniuConfig) {
        if (qiniuConfig == null) {
            return ResponseBean.error("上传失败,请先添加一个七牛云的配置！");
        }
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(QiniuUtils.getRegionByZone(qiniuConfig.getZone()));
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = qiniuConfig.getAccessKey();
        String secretKey = qiniuConfig.getSecretKey();
        String bucket = qiniuConfig.getBucket();
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        //默认不指定key的情况下，以文件内容的hash值作为文件名

        try {
            FileUtil.checkSize(maxSize, file.getSize());
            try {
                // 获取文件名
                String name = file.getOriginalFilename();
                // 如果通过这个文件名在数据库中能查到数据到话 继续上传的话文件名会重复 所以要改变一下文件名
                if (qiniuContentMapper.selectOne(new QueryWrapper<QiniuContent>().eq("name", name)) != null) {
                    name = QiniuUtils.createFileName(name);
                }
                Response response = uploadManager.put(file.getBytes(), name, upToken);
                //解析上传成功的结果
                DefaultPutRet putRet = JsonUtils.toBean(response.bodyString(), DefaultPutRet.class);
                // 将文件数据写入数据库
                QiniuContent qiniuContent = new QiniuContent();
                qiniuContent.setSuffix(FileUtil.getExtensionName(putRet.key));
                qiniuContent.setBucket(bucket);
                qiniuContent.setType(qiniuConfig.getType());
                qiniuContent.setUpdateTime(LocalDateTime.now());
                qiniuContent.setName(FileUtil.getFileNameNoEx(putRet.key));
                qiniuContent.setSize(FileUtil.getSize(Integer.parseInt(file.getSize() + "")));
                qiniuContent.setFileType(FileUtil.getFileType(qiniuContent.getSuffix()));
                qiniuContent.setUrl(qiniuConfig.getDomain() + "/" + putRet.key);
                qiniuContentMapper.insert(qiniuContent);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseBean.success("上传成功！");
    }

}
