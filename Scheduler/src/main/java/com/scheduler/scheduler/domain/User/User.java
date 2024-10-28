package com.scheduler.scheduler.domain.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scheduler.scheduler.domain.ToDo.ToDo;
import com.scheduler.scheduler.presentation.dto.user.response.UserCreateResponseDto;
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;    // 로그인 비밀번호
    @Column(nullable = false)
    private String name;    // 사용자 이름
    @Enumerated(EnumType.STRING)
    private Role role;  // 역할 -> 사용 권한
    @OneToMany
    private List<ToDo> todoList;

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

    public UserCreateResponseDto toCreateResponseDto(){
        return UserCreateResponseDto.builder()
                .name(name)
                .build();
    }
}
