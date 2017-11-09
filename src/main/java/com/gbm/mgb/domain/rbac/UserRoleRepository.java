package com.gbm.mgb.domain.rbac;

import com.gbm.mgb.core.Mapper;

import java.util.List;

/**
 * Created by Waylon on 2017/10/20.
 */
public interface UserRoleRepository extends Mapper<UserRole> {
    /**
     * 获取用户角色列表
     * @param userId
     * @return
     */
    List<Role> getRolesByUserId(String userId);
}
