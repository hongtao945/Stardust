package com.lht.admin.service;

import com.lht.admin.dto.UserAddDto;
import com.lht.admin.dto.UserQueryDto;
import com.lht.admin.pojo.RespondPageBean;
import com.lht.admin.pojo.ResponseBean;
import com.lht.admin.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 管理系统-后台用户表 服务类
 * </p>
 *
 * @author lht
 * @since 2021-03-20
 */
public interface IUserService extends IService<User> {

    /**
     * 修改密码
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 修改结果
     */
    ResponseBean resetPassword(String oldPassword, String newPassword);

    /**
     * 模糊查询用户列表
     * @param offset
     * @param limit
     * @param queryDto 查询参数
     * @return 用户列表
     */
    RespondPageBean fuzzyGetUserList(Integer offset, Integer limit, UserQueryDto queryDto);

    /**
     * 添加用户
     * @param userAddDto 用户
     * @return 是否添加成功
     */
    boolean addUser(UserAddDto userAddDto);

    /**
     * 删除用户
     * @param id 用户id
     * @return 是否删除成功
     */
    boolean deleteUser(Long id);
}
