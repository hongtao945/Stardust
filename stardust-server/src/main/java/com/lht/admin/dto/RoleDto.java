package com.lht.admin.dto;

import com.lht.admin.pojo.Role;

import java.util.List;

/**
 * @Author lht
 * @date 2021/3/23 - 20:48
 * @description:
 */
public class RoleDto extends Role {
    private static final long serialVersionUID = -5784234789156935003L;

    private List<Integer> menuIds;

    public List<Integer> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<Integer> menuIds) {
        this.menuIds = menuIds;
    }

}
