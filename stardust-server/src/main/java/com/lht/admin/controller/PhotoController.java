package com.lht.admin.controller;


import com.lht.admin.dto.PhotoQueryDto;
import com.lht.admin.pojo.PageTableRequest;
import com.lht.admin.pojo.Photo;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.pojo.ResponseBean;
import com.lht.admin.service.IPhotoService;
import com.lht.utils.BatchRemoveUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lht
 * @since 2021-03-25
 */
@Controller
@RequestMapping("/api/page/photo")
@Api(tags = "后台管理系统-相册管理")
public class PhotoController {
    @Resource
    private IPhotoService photoService;

    @GetMapping("/index")
    @ApiOperation(value = "跳转到图片管理页面")
    public String index() {
        return "system/photo/photo";
    }

    @GetMapping
    @ApiOperation(value = "模糊查询图片列表")
    @ResponseBody
    public RespondPageBean fuzzyGetPhotoList(PageTableRequest request, PhotoQueryDto queryDto) {
        return photoService.fuzzyGetPhotoList(request.getPage(), request.getLimit(), queryDto);
    }

    @GetMapping("/add")
    @ApiOperation(value = "打开添加照片页面")
    public String toAddPhoto() {
        return "system/photo/photo-add";
    }

    @PostMapping
    @ApiOperation(value = "添加一张照片")
    @ResponseBody
    public ResponseBean savePhoto(@RequestBody Photo photo) {
        photo.setCreateTime(LocalDateTime.now());
        photo.setUpdateTime(LocalDateTime.now());
        if (photoService.save(photo)) {
            return ResponseBean.success("添加成功!");
        }
        return ResponseBean.error("添加失败!");
    }

    @GetMapping("/edit")
    @ApiOperation(value = "打开编辑照片页面")
    public String toEditPhoto(Model model, Long photoId) {
        model.addAttribute("photo", photoService.getById(photoId));
        return "system/photo/photo-edit";
    }

    @PutMapping
    @ApiOperation(value = "更新照片")
    @ResponseBody
    public ResponseBean update(@RequestBody Photo photo) {
        photo.setUpdateTime(LocalDateTime.now());
        if (photoService.updateById(photo)) {
            return ResponseBean.success("更新成功!");
        }
        return ResponseBean.error("更新失败！");
    }

    @DeleteMapping("/{photoId}")
    @ApiOperation(value = "删除一张照片")
    @ResponseBody
    public ResponseBean deletePhotoById(@PathVariable Long photoId) {
        if (photoService.removeById(photoId)) {
            return ResponseBean.success("删除成功！");
        }
        return ResponseBean.success("删除失败！");
    }

    @DeleteMapping("batchRemove/{photoIds}")
    @ApiOperation(value = "批量删除照片")
    @ResponseBody
    public ResponseBean batchRemovePhotoByIds(@PathVariable String photoIds) {
        List<Long> photoIdList = BatchRemoveUtils.getLongTypeListByStr(photoIds);
        if (photoService.removeByIds(photoIdList)) {
            return ResponseBean.success("删除成功！");
        }
        return ResponseBean.success("删除失败！");
    }

}
