package com.scheduler.scheduler.application;

import com.scheduler.scheduler.presentation.dto.sign.SignInRequestDto;
import com.scheduler.scheduler.presentation.dto.sign.SignInResultDto;
import com.scheduler.scheduler.presentation.dto.sign.SignUpRequestDto;
import com.scheduler.scheduler.presentation.dto.sign.SignUpResultDto;

public interface SignService {
    SignUpResultDto signUp(SignUpRequestDto requestDto);

    SignInResultDto signIn(SignInRequestDto requestDto) throws RuntimeException;
}
