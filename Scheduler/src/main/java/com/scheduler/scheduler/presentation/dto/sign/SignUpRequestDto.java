package com.scheduler.scheduler.presentation.dto.sign;

import com.scheduler.scheduler.domain.User.Role;
import com.scheduler.scheduler.domain.User.User;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Builder
public class SignUpRequestDto {
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$",
            message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;
    private String name;

    public User toUser() {
        User user = User.builder()
                .email(email)
                .name(name)
                .password(password)
                .role(Role.USER)
                .build();
        return user;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
