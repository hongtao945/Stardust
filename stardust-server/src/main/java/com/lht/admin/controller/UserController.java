package com.lht.admin.controller;


import com.lht.admin.dto.ResetPasswordDto;
import com.lht.admin.dto.UserAddDto;
import com.lht.admin.dto.UserQueryDto;
import com.lht.admin.pojo.PageTableRequest;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.pojo.ResponseBean;
import com.lht.admin.pojo.User;
import com.lht.admin.service.INoticeService;
import com.lht.admin.service.IRoleService;
import com.lht.admin.service.IUserService;
import com.lht.admin.vo.PersonPageVo;
import com.lht.annotation.SysLog;
import com.lht.security.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * 管理系统-后台用户表 前端控制器
 * </p>
 *
 * @author lht
 * @since 2021-03-20
 */
@Controller
@RequestMapping("/api/user")
@Api(tags = "后台管理系统-用户管理")
public class UserController {

    @Resource
    private IUserService userService;
    @Resource
    private INoticeService noticeService;
    @Resource
    private IRoleService roleService;

    @GetMapping("/index")
    @ApiOperation(value = "跳转到用户管理页面")
    public String index() {
        return "system/user/user";
    }

    @GetMapping
    @ApiOperation(value = "模糊查询用户列表")
    @ResponseBody
    @SysLog("模糊查询用户列表")
    @PreAuthorize("hasAnyAuthority('user:list')")
    public RespondPageBean fuzzyGetUserList(PageTableRequest request, UserQueryDto queryDto) {
        return userService.fuzzyGetUserList(request.getPage(), request.getLimit(), queryDto);
    }

    @GetMapping("/person")
    @ApiOperation(value = "跳转到用户个人信息页面")
    public String person(Model model) {
        PersonPageVo pageVo = new PersonPageVo();
        pageVo.setNotices(noticeService.recentNotice());
        model.addAttribute("pageVo", pageVo);
        return "system/user/person";
    }

    @GetMapping("/add")
    @ApiOperation(value = "跳转到添加用户页面")
    public String add(Model model) {
        model.addAttribute("roleList", roleService.list());
        return "system/user/user-add";
    }

    @GetMapping("/reset")
    @ApiOperation(value = "打开修改密码框")
    public String reset() {
        return "system/user/reset";
    }

    @PutMapping("/reset")
    @ApiOperation(value = "修改密码")
    @ResponseBody
    @SysLog("修改密码")
    public ResponseBean resetPassword(@RequestBody ResetPasswordDto dto) {
        return userService.resetPassword(dto.getOldPassword(), dto.getNewPassword());
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改个人信息")
    @ResponseBody
    @SysLog("修改个人信息")
    public ResponseBean updateUserInfo(@RequestBody User user) {
        if (userService.updateById(user)) {
            return ResponseBean.success("更新成功!请重新登录");
        }
        return ResponseBean.error("更新失败!");
    }

    @PutMapping("/enable")
    @ApiOperation(value = "启用或封禁用户")
    @ResponseBody
    @SysLog("启用或封禁用户")
    @PreAuthorize("hasAnyAuthority('user:edit')")
    public ResponseBean enableUser(@RequestBody User user) {
        if (user.getUserId() == 1) {
            return ResponseBean.error("该用户是管理员!");
        }
        User user1 = ((LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        if (Objects.equals(user.getUserId(), user1.getUserId())) {
            return ResponseBean.error("你不会想封自己号吧?");
        }
        if (userService.updateById(user)) {
            if (user.getStatus()) {
                return ResponseBean.success("启用成功!");
            }
            return ResponseBean.success("封禁成功!");
        }
        return ResponseBean.error("未知错误!");
    }

    @PostMapping
    @ApiOperation(value = "添加用户")
    @ResponseBody
    @SysLog("添加用户")
    @PreAuthorize("hasAnyAuthority('user:add')")
    public ResponseBean addUser(@RequestBody UserAddDto userAddDto) {
        if (userService.addUser(userAddDto)) {
            return ResponseBean.success("用户添加成功,初始密码123456");
        }
        return ResponseBean.error("添加失败!");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户")
    @ResponseBody
    @SysLog("删除用户")
    @PreAuthorize("hasAnyAuthority('user:delete')")
    public ResponseBean deleteUser(@PathVariable Long id) {
        if (id == 1L) {
            return ResponseBean.error("该用户是管理员!");
        }
        User user1 = ((LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        if (Objects.equals(id, user1.getUserId())) {
            return ResponseBean.error("你不会想把自己删了吧?");
        }
        if (userService.deleteUser(id)) {
            return ResponseBean.success("用户删除成功!");
        }
        return ResponseBean.error("删除失败!");
    }

}
