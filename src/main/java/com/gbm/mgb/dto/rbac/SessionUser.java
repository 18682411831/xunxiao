package com.gbm.mgb.dto.rbac;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 登陆用户信息
 *
 * @author waylon
 * @date 2017/11/02
 **/
public class SessionUser implements UserDetails{
    private final String id;
    /**
     * 帐号
     */
    private final String account;
    /**
     * 密码
     */
    private final String password;
    /**
     * 角色集合
     */
    private final Collection<? extends GrantedAuthority> authorities;

    public SessionUser(String id, String account, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * 返回分配给用户的角色列表
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return account;
    }
    // 账户是否未过期
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    // 账户是否未锁定
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    // 密码是否未过期
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    // 账户是否激活
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
