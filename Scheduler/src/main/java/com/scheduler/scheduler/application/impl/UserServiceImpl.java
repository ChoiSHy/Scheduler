package com.scheduler.scheduler.application.impl;

import com.scheduler.scheduler.application.UserService;
import com.scheduler.scheduler.domain.User.User;
import com.scheduler.scheduler.infrastructure.repository.UserRepository;
import com.scheduler.scheduler.presentation.dto.user.UserRequestDto;
import com.scheduler.scheduler.presentation.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserDetails loadUserByUsername(String username) {
        LOGGER.info("[loadUserByUsername] loadUserByUsername 수행. id: {}", username);
        return userRepository.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다."));
    }

    @Override
    public UserResponseDto getUserByUid(UserRequestDto requestDto) {
        LOGGER.info("[UserDetailService - getUserByUid] : ** START ** id: {}", requestDto.getEmail());

        User foundUser =  userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> {
                    LOGGER.error("[UserDetailService - getUserByUid] : ** cannot find that user **");
                    throw new NoSuchElementException("회원 정보를 찾을 수 없습니다.");
                });

        LOGGER.info("[UserDetailService - getUserByUid] : finding success - " + foundUser);

        UserResponseDto responseDto = foundUser.toResponseDto();
        LOGGER.info("[UserDetailService - getUserByUid] : User -> UserResponseDto");

        LOGGER.info("[UserDetailService - getUserByUid] : ** DONE **");
        return responseDto;
    }

}
