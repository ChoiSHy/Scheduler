package com.scheduler.scheduler.application;

import com.scheduler.scheduler.presentation.dto.user.UserRequestDto;
import com.scheduler.scheduler.presentation.dto.user.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.NoSuchElementException;

public interface UserService{
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    UserResponseDto getUserByUid(UserRequestDto requestDto) throws NoSuchElementException;
}
