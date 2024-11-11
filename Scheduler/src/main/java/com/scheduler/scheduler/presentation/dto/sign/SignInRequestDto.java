package com.scheduler.scheduler.presentation.dto.sign;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInRequestDto {
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$",
            message = "이메일 형식이 올바르지 않습니다.")
    @Schema(defaultValue = "user1@email.com")
    private String email;
    @Schema(defaultValue = "qwer1234!")
    private String password;

}
