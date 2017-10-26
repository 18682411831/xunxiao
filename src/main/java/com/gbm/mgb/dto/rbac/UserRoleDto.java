package com.gbm.mgb.dto.rbac;

import com.gbm.mgb.domain.rbac.Role;
import com.gbm.mgb.domain.rbac.User;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Waylon on 2017/10/19.
 */
@Data
public class UserRoleDto {
    /**
     * 用户信息
     */
    private User user;
    /**
     * 角色信息
     */
    private Role role;

}
