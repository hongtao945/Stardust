package com.lht.admin.mapper;

import com.lht.admin.dto.MenuDto;
import com.lht.admin.dto.MenuIndexDto;
import com.lht.admin.pojo.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author lht
 * @since 2021-03-20
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 模糊查询菜单
     * @param queryName
     * @param queryType
     * @return
     */
    List<Menu> getAllMenus(@Param("queryName") String queryName, @Param("queryType") Integer queryType);

    List<MenuDto> buildAllMenuTree();

    List<MenuIndexDto> getMenuByUserId(@Param("userId") Long userId);

    /**
     * 通过角色的id绘制菜单树
     * @param roleId 角色的id
     * @return
     */
    List<MenuDto> buildMenuTreeByRoleId(@Param("roleId") Long roleId);
}
