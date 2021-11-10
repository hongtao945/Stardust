package com.lht.admin.service;

import com.lht.admin.dto.MenuDto;
import com.lht.admin.dto.MenuIndexDto;
import com.lht.admin.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author lht
 * @since 2021-03-20
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> getAllMenus(String queryName, Integer queryType);

    /**
     * 构建菜单树
     * @return
     */
    List<MenuDto> buildAllMenuTree();

    /**
     * 通过用户id获取对应的菜单 是用于前端后台管理的菜单绘制
     * @param userId
     * @return
     */
    List<MenuIndexDto> getMenuByUserId(Long userId);

    /**
     * 通过角色id获得相应的菜单树
     * @param roleId
     * @return
     */
    List<MenuDto> buildMenuTreeByRoleId(Long roleId);

    /**
     * 通过用户id获取对应的菜单 是后端安全框架为用户查询权限时使用的
     * @param userId
     * @return
     */
    List<MenuIndexDto> getMenuBsyUserId(Long userId);

    /**
     * 添加菜单
     * @param menu 菜单
     * @return 是否添加成功
     */
    boolean saveMenu(Menu menu);

    /**
     * 删除一个菜单
     * @param id 菜单id
     * @return 是否删除成功
     */
    boolean removeMenu(Integer id);

    /**
     * 更新菜单
     * @param menu 菜单
     * @return 是否更新成功
     */
    boolean updateMenu(Menu menu);
}
