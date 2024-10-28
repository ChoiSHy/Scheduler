package com.scheduler.scheduler.presentation.dto.user.request;

import com.scheduler.scheduler.domain.User.Role;
import com.scheduler.scheduler.domain.User.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequestDto {
    private String uid; // 로그인 아이디
    private String password; //로그인 비밀번호
    private String name;

    public User toEntity(){
        return User.builder()
                .uid(uid)
                .password(password)
                .name(name)
                .role(Role.USER)
                .build();
    }
}
