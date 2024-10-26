package com.scheduler.scheduler.domain.User;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.scheduler.scheduler.domain.User.Permission.*;
@Getter
public enum Role {
    ADMIN(Set.of(
            READ,
            CREATE,
            UPDATE,
            DELETE)
    ),
    MANAGER(Set.of(
            READ,
            CREATE,
            UPDATE)),
    USER(Set.of(READ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions){
        this.permissions = permissions;
    }

    public List<SimpleGrantedAuthority> getAuthorities(){
        List<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));

        return authorities;
    }
}
