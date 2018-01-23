package com.gbm.mgb.domain.rbac;

import com.gbm.mgb.domain.base.BaseBean;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

/**
 * 组织
 *
 * @author waylon
 * @date 2017/11/02
 **/
@Table(name = "gbm_seed_organization")
@Data
@NoArgsConstructor
public class Organization extends BaseBean{
    private String name;
    private String state;
}
