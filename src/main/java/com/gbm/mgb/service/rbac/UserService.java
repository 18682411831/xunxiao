package com.gbm.mgb.service.rbac;

import com.gbm.mgb.core.ServiceException;
import com.gbm.mgb.domain.rbac.User;
import com.gbm.mgb.domain.rbac.UserRepository;
import com.gbm.mgb.core.AbstractService;
import com.gbm.mgb.domain.rbac.UserRoleRepository;
import com.gbm.mgb.dto.rbac.UserRoleDto;
import com.gbm.mgb.helper.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * 用户服务
 * @author waylon
 * @date 2017/11/02
 **/
@Service
public class UserService extends AbstractService<User> implements com.gbm.mgb.core.Service<User> {

    @Resource
    private UserRepository userRepository;
    @Resource
    private UserRoleRepository userRoleRepository;

    /**
     * 保存用户
     * @param userRole
     */
    @Transactional(rollbackFor=Exception.class)
    public void saveUser(UserRoleDto userRole){
        //用户保存
        String userId = ObjectId.genGUID();
        User user = userRole.getUser();
        user.setId(userId);
        //order.setOrganization("2");
        int result = userRepository.insert(user);
        if(result<=0){
            throw new ServiceException("用户保存失败");
        }
        //保存角色关联信息
        //userRoleRepository.insert(new UserRole(userId,userRole.getRole().getId()));
    }

    public User doLogin(String email,String password){
        User user = userRepository.findUserByUsernameAndPassword(new User(email,password));
        return user;
    }




}
