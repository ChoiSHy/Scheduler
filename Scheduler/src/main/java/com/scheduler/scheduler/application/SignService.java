package com.scheduler.scheduler.application;

import com.scheduler.scheduler.presentation.dto.sign.*;
import com.scheduler.scheduler.presentation.dto.sign.password.PasswordChangeRequestDto;
import com.scheduler.scheduler.presentation.dto.sign.password.PasswordResponseDto;

public interface SignService {
    SignUpResultDto signUp(SignUpRequestDto requestDto);
    SignInResultDto signIn(SignInRequestDto requestDto) throws RuntimeException;
    PasswordResponseDto changePasswordMyself(PasswordChangeRequestDto requestDto);
    PasswordResponseDto resetPasswordMyself();
}
