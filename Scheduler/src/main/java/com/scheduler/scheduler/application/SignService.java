package com.scheduler.scheduler.application;

import com.scheduler.scheduler.presentation.dto.sign.SignInResultDto;
import com.scheduler.scheduler.presentation.dto.sign.SignUpResultDto;

public interface SignService {
    SignUpResultDto signUp(String id, String password, String name, String role);
    SignInResultDto signIn(String id, String password) throws RuntimeException;
}
