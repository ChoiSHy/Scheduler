package com.scheduler.scheduler.application;

import com.scheduler.scheduler.presentation.dto.user.modify.AdminUserInfoModifyRequestDto;
import com.scheduler.scheduler.presentation.dto.user.modify.MyUserInfoModifyRequestDto;
import com.scheduler.scheduler.presentation.dto.user.UserInfoRequestDto;
import com.scheduler.scheduler.presentation.dto.user.UserInfoResponseDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.NoSuchElementException;

public interface UserService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    UserInfoResponseDto getUserByEmail(UserInfoRequestDto requestDto) throws NoSuchElementException;
    UserInfoResponseDto modifyUserInfoByEmail(AdminUserInfoModifyRequestDto requestDto);
    void removeUserByEmail(UserInfoRequestDto requestDto);
    UserInfoResponseDto getMyUserInfo() throws NoSuchElementException;
    UserInfoResponseDto modifyMyUserInfo(MyUserInfoModifyRequestDto requestDto);
    void removeMyself();
}
