package com.lht.admin.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.lht.admin.dto.RoleDto;
import com.lht.admin.pojo.PageTableRequest;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.pojo.ResponseBean;
import com.lht.admin.pojo.Role;
import com.lht.admin.service.IRoleService;
import com.lht.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * <p>
 * 角色信息表 前端控制器
 * </p>
 *
 * @author lht
 * @since 2021-03-20
 */
@Controller
@RequestMapping("/api/role")
@Api(tags = "后台管理系统-角色管理")
public class RoleController {

    @Resource
    private IRoleService roleService;

    @GetMapping("/index")
    @ApiOperation(value = "返回角色页面")
    public String index() {
        return "system/role/role";
    }

    @GetMapping
    @ApiOperation(value = "模糊查询角色列表")
    @ResponseBody
    @SysLog("模糊查询角色列表")
    public RespondPageBean roleList(PageTableRequest pageRequest, String roleName) {
        return roleService.fuzzyGetRolesByPage(pageRequest.getPage(), pageRequest.getLimit(), roleName);
    }

    @GetMapping("/add")
    @ApiOperation(value = "跳转到添加角色页面")
    public String toAddRole(Model model) {
        model.addAttribute("Role",new Role());
        return "system/role/role-add";
    }

    @PostMapping
    @ApiOperation(value = "添加一个角色")
    @ResponseBody
    @SysLog("添加一个角色")
    @PreAuthorize("hasAnyAuthority('role:add')")
    public ResponseBean addRole(@RequestBody RoleDto role) {
        return roleService.addRole(role);
    }

    @GetMapping("/edit")
    @ApiOperation(value = "跳转到编辑角色页面")
    @SysLog("跳转到编辑角色页面")
    public String toEditRole(Model model, Integer roleId) {
        model.addAttribute("Role", roleService.getById(roleId));
        return "system/role/role-edit";
    }

    @PutMapping
    @ApiOperation(value = "编辑一个角色")
    @ResponseBody
    @SysLog("编辑一个角色")
    @PreAuthorize("hasAnyAuthority('role:edit')")
    public ResponseBean editRole(@RequestBody RoleDto role) {
        return roleService.updateRole(role);
    }

    @DeleteMapping("/{roleId}")
    @ApiOperation(value = "删除一个角色")
    @ResponseBody
    @SysLog("删除一个角色")
    @PreAuthorize("hasAnyAuthority('role:delete')")
    public ResponseBean deleteRoleByRoleId(@PathVariable Long roleId) {
        return roleService.deleteRoleByRoleId(roleId);
    }

}
