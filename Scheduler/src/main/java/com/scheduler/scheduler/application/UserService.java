package com.scheduler.scheduler.application;

import com.scheduler.scheduler.presentation.dto.user.request.UserCreateRequestDto;
import com.scheduler.scheduler.presentation.dto.user.response.UserCreateResponseDto;
import com.scheduler.scheduler.presentation.dto.user.response.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
    UserCreateResponseDto saveUser(UserCreateRequestDto requestDto);
    UserResponseDto getUserById(Long id);
}
