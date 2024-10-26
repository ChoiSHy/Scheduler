package com.scheduler.scheduler.domain.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // DB상 주키
    @Column(nullable = false, unique = true)
    private String uid; // 로그인 아이디
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;    // 로그인 비밀번호
    @Column(nullable = false)
    private String name;    // 사용자 이름

    @Enumerated(EnumType.STRING)
    private Role role;  // 역할 -> 사용 권한

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }
    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.uid;
    }
}
