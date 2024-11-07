package com.scheduler.scheduler.application;

import com.scheduler.scheduler.presentation.dto.user.request.UserCreateRequestDto;
import com.scheduler.scheduler.presentation.dto.user.response.UserCreateResponseDto;
import com.scheduler.scheduler.presentation.dto.user.response.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService{
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    UserCreateResponseDto saveUser(UserCreateRequestDto requestDto);
    UserResponseDto getUserById(Long id);
}
