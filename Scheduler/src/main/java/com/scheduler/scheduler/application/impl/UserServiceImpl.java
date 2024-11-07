package com.scheduler.scheduler.application.impl;

import com.scheduler.scheduler.application.UserService;
import com.scheduler.scheduler.domain.User.User;
import com.scheduler.scheduler.infrastructure.repository.UserRepository;
import com.scheduler.scheduler.presentation.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserDetails loadUserByUsername(String username) {
        LOGGER.info("[loadUserByUsername] loadUserByUsername 수행. id: {}",username);
        return userRepository.getByUid(username);
    }

    public UserResponseDto getUserById(Long id) {
        LOGGER.info("[UserDetailService - getUserById] : ** START **");
        Optional<User> optional = userRepository.findById(id);
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
