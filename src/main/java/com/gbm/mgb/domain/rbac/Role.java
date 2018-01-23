package com.gbm.mgb.domain.rbac;

import com.gbm.mgb.domain.base.BaseBean;
import lombok.Data;

import javax.persistence.Table;

/**
 * Created by Waylon on 2017/10/18.
 */
@Table(name = "gbm_seed_role")
@Data
public class Role extends BaseBean{
    /**
     * 角色名
     */
    private String name;

    /**
     * 状态 1：启用 2 :停用
     */
    private String state;
}
