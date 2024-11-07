package com.scheduler.scheduler.presentation.dto.sign;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInRequestDto {
    private String id;
    private String password;

}
