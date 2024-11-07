package com.scheduler.scheduler.domain.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scheduler.scheduler.domain.Todo.Todo;
import com.scheduler.scheduler.presentation.dto.user.UserResponseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // DB상 주키
    @Column(nullable = false, unique = true)
    private String uid; // 로그인 아이디
    @Column(nullable = false)
    private String password;    // 로그인 비밀번호
    @Column(nullable = false)
    private String name;    // 사용자 이름
    @Enumerated(EnumType.STRING)
    private Role role;  // 역할 -> 사용 권한
    @OneToMany
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private List<Todo> todoList;


    public String getPassword() {
        return null;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getUsername() {
        return this.uid;
    }

    public UserResponseDto toResponseDto() {
        return new UserResponseDto(name, uid);
    }

    public Role getRole() {
        return role;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
        //return UserDetails.super.isAccountNonExpired();
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
        //return UserDetails.super.isAccountNonLocked();
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
        //return UserDetails.super.isCredentialsNonExpired();
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
        //return UserDetails.super.isEnabled();
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
