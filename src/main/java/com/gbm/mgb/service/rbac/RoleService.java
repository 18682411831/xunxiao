package com.gbm.mgb.service.rbac;

import com.gbm.mgb.core.AbstractService;
import com.gbm.mgb.domain.rbac.Role;
import com.gbm.mgb.domain.rbac.RoleRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Waylon on 2017/10/18.
 */
@Service
public class RoleService extends AbstractService<Role> implements com.gbm.mgb.core.Service<Role> {
    @Resource
    private RoleRepository roleRepository;
}
