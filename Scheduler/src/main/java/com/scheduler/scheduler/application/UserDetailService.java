package com.scheduler.scheduler.application;

import com.scheduler.scheduler.domain.User.User;
import com.scheduler.scheduler.infrastructure.UserRepository;
import com.scheduler.scheduler.presentation.dto.user.request.UserCreateRequestDto;
import com.scheduler.scheduler.presentation.dto.user.response.UserCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(UserDetailService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserCreateResponseDto saveUser(UserCreateRequestDto requestDto){
        LOGGER.info("[UserDetailService - saveUser] : ** START **");
        User requestedUser = requestDto.toEntity();
        LOGGER.info("[UserDetailService - saveUser] : toEntity() = "+requestedUser);
        User savedUser = userRepository.save(requestedUser);
        LOGGER.info("[UserDetailService - saveUser] : save() = "+savedUser);
        UserCreateResponseDto savedUserDto = savedUser.toCreateResponseDto();
        LOGGER.info("[UserDetailService - saveUser] : toCreateResponseDto() = "+savedUserDto);
        LOGGER.info("[UserDetailService - saveUser] : ** DONE **");
        return savedUserDto;
    }

}