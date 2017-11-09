package com.gbm.mgb.domain.rbac;

import com.gbm.mgb.core.Mapper;

/**
 * Created by Waylon on 2017/10/11.
 */
public interface UserRepository extends Mapper<User> {

    /**
     * 用户名和密码查询用户
     * @param user
     * @return
     */
    User findUserByUsernameAndPassword(User user);

}
