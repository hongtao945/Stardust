package com.lht.admin.controller;


import com.lht.admin.dto.MenuDto;
import com.lht.admin.pojo.Menu;
import com.lht.admin.pojo.ResponseBean;
import com.lht.admin.service.IMenuService;
import com.lht.annotation.SysLog;
import com.lht.constant.Constant;
import com.lht.utils.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author lht
 * @since 2021-03-20
 */
@Controller
@RequestMapping("/api/menu")
@Api(tags = "后台管理系统-菜单管理")
public class MenuController {

    @Resource
    private IMenuService menuService;

    @GetMapping("/index")
    @ApiOperation(value = "返回菜单页面")
    public String index() {
        return "system/menu/menu";
    }

    @GetMapping
    @ResponseBody
    @ApiOperation(value = "获取菜单列表")
    @SysLog("获取菜单列表")
    public ResponseBean getAllMenus(String queryName,Integer queryType) {
        List<Menu> menuList = menuService.getAllMenus(queryName,queryType);
        ResponseBean bean = ResponseBean.success("菜单获取成功", menuList);
        bean.setCode(ResultCode.TABLE_SUCCESS);
        return bean;
    }

    @GetMapping("/build")
    @ApiOperation(value = "构建菜单树")
    @ResponseBody
    @SysLog("构建菜单树")
    public ResponseBean buildAllMenuTree() {
        List<MenuDto> treeList = menuService.buildAllMenuTree();
        return ResponseBean.success("构造完成",treeList);
    }

    @GetMapping("/buildByRid/{roleId}")
    @ApiOperation(value = "通过角色的id绘制菜单树")
    @ResponseBody
    @SysLog("通过角色的id绘制菜单树")
    public ResponseBean buildMenuTreeByRoleId(@PathVariable Long roleId) {
        List<MenuDto> treeList = menuService.buildMenuTreeByRoleId(roleId);
        return ResponseBean.success("构造完成",treeList);
    }

    @GetMapping("/add")
    @ApiOperation(value = "打开添加菜单页面")
    @SysLog("打开添加菜单页面")
    public String toAddMenu(Model model) {
        model.addAttribute("myMenu", new Menu());
        return "system/menu/menu-add";
    }

    @PostMapping
    @ApiOperation(value = "添加一个菜单权限")
    @ResponseBody
    @SysLog("添加一个菜单权限")
    @PreAuthorize("hasAnyAuthority('menu:add')")
    public ResponseBean addMenu(@RequestBody Menu menu) {
        menu.setCreateTime(LocalDateTime.now());
        menu.setUpdateTime(LocalDateTime.now());
        if (!StringUtils.isEmpty(menu.getIcon()) && !menu.getIcon().startsWith("layui-icon ")) {
            menu.setIcon("layui-icon " + menu.getIcon());
        }
        if (menuService.saveMenu(menu)) {
            return ResponseBean.success("添加成功！");
        }
        return ResponseBean.error("添加失败！");
    }

    @GetMapping("/edit")
    @ApiOperation(value = "打开编辑菜单页面")
    @SysLog("打开编辑菜单页面")
    public String toEditMenu(Model model, Menu menu) {
        model.addAttribute("myMenu",menuService.getById(menu.getMenuId()));
        return "system/menu/menu-edit";
    }

    @PutMapping
    @ApiOperation(value = "更新一个菜单权限")
    @ResponseBody
    @SysLog("更新一个菜单权限")
    @PreAuthorize("hasAnyAuthority('menu:edit')")
    public ResponseBean editMenu(@RequestBody Menu menu) {
        menu.setUpdateTime(LocalDateTime.now());
        if (menuService.updateMenu(menu)) {
            return ResponseBean.success("更新成功！");
        }
        return ResponseBean.error("更新失败！");
    }

    @DeleteMapping("/{menuId}")
    @ApiOperation(value = "删除一个菜单权限")
    @ResponseBody
    @SysLog("删除一个菜单权限")
    @PreAuthorize("hasAnyAuthority('menu:delete')")
    public ResponseBean deleteMenu(@PathVariable(value = "menuId") Integer id) {
        if (menuService.removeMenu(id)) {
            return ResponseBean.success("删除成功！");
        }
        return ResponseBean.error("删除失败！");
    }

}
