package com.yys.po;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by xyr on 2017/10/16.
 */
@Entity
@Data
public class Administrator extends Login implements Serializable{
    private static final long serialVersionUID = -1100401797719392061L;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(Role_Admin));
    }
}
