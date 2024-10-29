package com.scheduler.scheduler.application;

import com.scheduler.scheduler.domain.User.Role;
import com.scheduler.scheduler.domain.User.User;
import com.scheduler.scheduler.infrastructure.UserRepository;
import com.scheduler.scheduler.presentation.dto.user.request.UserCreateRequestDto;
import com.scheduler.scheduler.presentation.dto.user.response.UserCreateResponseDto;
import com.scheduler.scheduler.presentation.dto.user.response.UserResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserDetailServiceTest {
    private UserRepository userRepository = Mockito.mock(UserRepository.class);
    private UserDetailService userDetailService;
    private Logger LOGGER = LoggerFactory.getLogger(UserDetailServiceTest.class);

    @BeforeEach
    public void setup() {
        userDetailService = new UserDetailService(userRepository);
    }

    @Test
    void saveUser() {
        LOGGER.info("**** [saveUser] ****");
        Mockito.when(userRepository.save(any(User.class))).then(returnsFirstArg());

        UserCreateResponseDto savedUserDto = userDetailService.saveUser(new UserCreateRequestDto("id", "password", "myName"));
        Assertions.assertEquals(savedUserDto.getName(), "myName");
    }

    @Test
    void getUserById() {
        LOGGER.info("**** [getUserById] ****");
        Mockito.when(userRepository.findUserById(1L))
                .thenReturn(Optional.of(User.builder()
                        .id(1L)
                        .uid("test-id")
                        .name("test-user")
                        .role(Role.USER)
                        .build()));


        UserResponseDto userResponseDto1 = userDetailService.getUserById(1L);
        Assertions.assertEquals(userResponseDto1.getUid(), "test-id");
        Assertions.assertEquals(userResponseDto1.getName(), "test-user");

    }
    @Test
    @DisplayName("getUserById - cannot find user")
    void getUserById_exceptionTest() {
        Mockito.when(userRepository.findUserById(2L))
                .thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(NoSuchElementException.class, () -> userDetailService.getUserById(2L));
    }
}