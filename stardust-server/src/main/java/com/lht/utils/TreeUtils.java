package com.lht.utils;

import com.lht.admin.dto.MenuDto;
import com.lht.admin.dto.MenuIndexDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author lht
 * @date 2021/3/22 - 22:41
 * @description: 构建菜单树的工具类
 */
public class TreeUtils {
    /**
     * @param list 通过用户的id查询出来的数据
     * @return 封装好的菜单对象
     */
    public static List<MenuIndexDto> parseMenuTree(List<MenuIndexDto> list) {
        List<MenuIndexDto> result = new ArrayList<>();
        list.forEach(v -> {
            // 先遍历整个数组，找出里面的父级菜单，即pid为0的那些
            if (v.getParentId() == 0) {
                result.add(v);
            }
        });
        result.forEach(v -> {
            // 找出上一步找出来的父菜单中的子菜单
            v = findChildren(v, list);
        });
        return result;
    }

    private static MenuIndexDto findChildren(MenuIndexDto v, List<MenuIndexDto> list) {
        List<MenuIndexDto> children = new ArrayList<>();
        list.forEach(child -> {
            if (Objects.equals(v.getId(), child.getParentId())) {
                children.add(child);
            }
        });
        v.setChildren(children);
        return v;
    }

    /**
     * 得到一个角色的权限树
     * @param listByRoleId
     * @param allMenus
     * @return
     */
    public static List<MenuDto> getCheckMenuTree(List<MenuDto> listByRoleId, List<MenuDto> allMenus) {
        List<Long> collect = listByRoleId.stream().map(MenuDto::getId).collect(Collectors.toList());
        List<Long> collect1 = allMenus.stream().map(MenuDto::getId).collect(Collectors.toList());
        collect.forEach(v-> {
            if (collect1.contains(v)) {
                int index = collect1.indexOf(v);
                allMenus.get(index).setCheckArr("1");
            }
        });
        return allMenus;
    }
}
