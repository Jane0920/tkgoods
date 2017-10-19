package com.yys.po;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * 所有的角色都继承与该类用于登录
 * Created by xyr on 2017/10/16.
 */
@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Login implements UserDetails{

    public final static String Role_User_Value = "USER";
    public final static String Role_User = "ROLE_" + Role_User_Value;
    public final static String Role_Admin_Value = "ADMIN";
    public final static String Role_Admin = "ROLE_" + Role_Admin_Value;

    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    private boolean enabled = true;

    /*private Collection<? extends GrantedAuthority> authorities;*/

    public Login() {
    }

    public Login(String id, String username, String password, boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "MyUserDetails [id=" + id + ", username=" + username
                + ", password=" + password + ", enabled=" + enabled + "]";
    }
}
