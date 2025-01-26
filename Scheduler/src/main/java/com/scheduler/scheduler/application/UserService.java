package com.scheduler.scheduler.application;

import com.scheduler.scheduler.presentation.dto.user.UserModifyRequestDto;
import com.scheduler.scheduler.presentation.dto.user.UserRequestDto;
import com.scheduler.scheduler.presentation.dto.user.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.NoSuchElementException;

public interface UserService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    UserResponseDto getUserByEmail(UserRequestDto requestDto) throws NoSuchElementException;

    UserResponseDto getMyUserInfo() throws NoSuchElementException;
    UserResponseDto modifyMyUserInfo(UserModifyRequestDto requestDto);
    void removeUserById(Long id);
    void removeMyself();
}
