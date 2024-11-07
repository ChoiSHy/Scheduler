package com.scheduler.scheduler.application;

import com.scheduler.scheduler.presentation.dto.user.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService{
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    UserResponseDto getUserById(Long id);
}
