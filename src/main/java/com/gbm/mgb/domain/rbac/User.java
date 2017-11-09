package com.gbm.mgb.domain.rbac;

import com.gbm.mgb.domain.base.BaseBean;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;


/**
 * Created by Waylon on 2017/10/11.
 */
@Table(name = "gbm_seed_user")
@Data
@NoArgsConstructor
public class User extends BaseBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    @Size(min = 2,max = 30, message = "用户名长度在 2-30位之间")
    private String name;

    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    @Size(min = 6,max = 15,message = "密码长度为 6-15个字符")
    private String password;

    /**
     * 邮箱
     */
    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9_-]+@\\\\w+\\\\.[a-z]+(\\\\.[a-z]+)?")
    private String email;

    /**
     * 性别
     */
    @NotNull
    @Min(1)
    private String sex;

    /**
     * 状态 1：启用 2 :停用
     */
    @NotNull
    @Min(1)
    private String state;

    /**
     * 组织编号
     */
    @Transient
    private Organization organization;

    /**
     * 角色列表
     */
    @Transient
    private List<Role> roles;

    public User(String email,String password){
        this.email = email;
        this.password = password;
    }

    public User(String id){
        this.id = id;
    }

}
