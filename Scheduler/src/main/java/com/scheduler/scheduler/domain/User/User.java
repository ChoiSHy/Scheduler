package com.scheduler.scheduler.domain.User;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scheduler.scheduler.domain.Schedule.Schedule;
import com.scheduler.scheduler.domain.Todo.Todo;
import com.scheduler.scheduler.presentation.dto.user.UserResponseDto;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
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
    private String email; // 로그인 아이디
    @Column(nullable = false)
    @ToString.Exclude
    private String password;    // 로그인 비밀번호
    @Column(nullable = false)
    private String name;    // 사용자 이름
    @Enumerated(EnumType.STRING)
    private Role role;  // 역할 -> 사용 권한
    @Column(nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;
    @OneToMany
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private List<Todo> todoList;
    @OneToMany
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private List<Schedule> scheduleList;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getUsername() {
        return this.email;
    }

    public UserResponseDto toResponseDto() {
        return UserResponseDto.builder()
                .email(email)
                .name(name)
                .birthDate(birthDate)
                .build();
    }

    public Role getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
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
