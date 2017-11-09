package com.gbm.mgb.helper;

import com.gbm.mgb.domain.rbac.User;

/**
 * Session Local
 * @author waylon
 * @date 2017/11/2
 */
public class SessionThreadLocalHelper {

    public static ThreadLocal<User> userThreadLocal = new ThreadLocal<User>();


}
