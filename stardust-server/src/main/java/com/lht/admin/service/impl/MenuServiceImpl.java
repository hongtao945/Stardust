package com.lht.admin.service.impl;

import com.lht.admin.dto.MenuDto;
import com.lht.admin.dto.MenuIndexDto;
import com.lht.admin.mapper.RoleMenuMapper;
import com.lht.admin.pojo.Menu;
import com.lht.admin.mapper.MenuMapper;
import com.lht.admin.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lht.utils.TreeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author lht
 * @since 2021-03-20
 */
@Service
@CacheConfig(cacheNames = "menu")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAllMenus(String queryName, Integer queryType) {
        return menuMapper.getAllMenus(queryName, queryType);
    }

    @Override
    public List<MenuDto> buildAllMenuTree() {
        return menuMapper.buildAllMenuTree();
    }

    @Override
    @Cacheable
    public List<MenuIndexDto> getMenuByUserId(Long userId) {
        List<MenuIndexDto> list = menuMapper.getMenuByUserId(userId);
        List<MenuIndexDto> result = TreeUtils.parseMenuTree(list);
        return result;
    }

    @Override
    @Cacheable
    public List<MenuDto> buildMenuTreeByRoleId(Long roleId) {
        List<MenuDto> listByRoleId = menuMapper.buildMenuTreeByRoleId(roleId);
        List<MenuDto> allMenus = buildAllMenuTree();
        List<MenuDto> result = TreeUtils.getCheckMenuTree(listByRoleId, allMenus);
        return result;
    }

    @Override
    public List<MenuIndexDto> getMenuBsyUserId(Long userId) {
        List<MenuIndexDto> list = menuMapper.getMenuByUserId(userId);
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean saveMenu(Menu menu) {
        return menuMapper.insert(menu) != 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean removeMenu(Integer id) {
        return menuMapper.deleteById(id) != 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean updateMenu(Menu menu) {
        return menuMapper.updateById(menu) != 0;
    }
}
