package com.scheduler.scheduler.application.impl;

import com.scheduler.scheduler.application.UserService;
import com.scheduler.scheduler.domain.User.User;
import com.scheduler.scheduler.infrastructure.repository.UserRepository;
import com.scheduler.scheduler.presentation.dto.user.request.UserCreateRequestDto;
import com.scheduler.scheduler.presentation.dto.user.response.UserCreateResponseDto;
import com.scheduler.scheduler.presentation.dto.user.response.UserResponseDto;
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

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return null;
    }


    public UserCreateResponseDto saveUser(UserCreateRequestDto requestDto) {
        LOGGER.info("[UserDetailService - saveUser] : ** START **");
        User requestedUser = requestDto.toEntity();
        LOGGER.info("[UserDetailService - saveUser] : toEntity() = " + requestedUser);
        User savedUser = userRepository.save(requestedUser);
        LOGGER.info("[UserDetailService - saveUser] : save() = " + savedUser);
        UserCreateResponseDto savedUserDto = savedUser.toCreateResponseDto();
        LOGGER.info("[UserDetailService - saveUser] : toCreateResponseDto() = " + savedUserDto);
        LOGGER.info("[UserDetailService - saveUser] : ** DONE **");
        return savedUserDto;
    }

    public UserResponseDto getUserById(Long id) {
        LOGGER.info("[UserDetailService - getUserById] : ** START **");
        Optional<User> optional = userRepository.findUserById(id);
        User foundUser=null;
        UserResponseDto responseDto=null;
        try {
            foundUser = optional.orElseThrow();
            LOGGER.info("[UserDetailService - getUserById] : finding success - "+foundUser.toString());
            responseDto = foundUser.toResponseDto();
            LOGGER.info("[UserDetailService - getUserById] : User -> UserResponseDto");
        }catch (NoSuchElementException ex){
            LOGGER.error("[UserDetailService - getUserById] : ** cannot find that user **");
            throw ex;
        }
        LOGGER.info("[UserDetailService - getUserById] : ** DONE **");
        return responseDto;
    }

}
