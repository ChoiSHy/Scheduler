package com.scheduler.scheduler.presentation.dto.sign;

import com.scheduler.scheduler.domain.User.Role;
import com.scheduler.scheduler.domain.User.User;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class SignUpRequestDto {
    private String id;
    private String password;
    private String name;

    public User toUser( ){
        User user = User.builder()
                .uid(id)
                .name(name)
                .role(Role.USER)
                .build();
        return user;
    }
}
