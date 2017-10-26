package com.gbm.mgb.domain.rbac;

import com.gbm.mgb.domain.base.BaseBean;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

/**
 * Created by Waylon on 2017/10/20.
 */
@Table(name = "gbm_seed_user_role")
@Data
@NoArgsConstructor
public class UserRole extends BaseBean{
    /**
     * 用户编号
     */
    private String userId;
    /**
     * 角色编号
     */
    private String roleId;
    /**
     * 状态 0：启用 1：停用
     */
    private String state;

    public UserRole(String userId,String roleId){
        this.userId = userId;
        this.roleId = roleId;
    }
}
