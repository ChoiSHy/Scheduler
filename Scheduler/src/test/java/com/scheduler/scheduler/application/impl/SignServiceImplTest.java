package com.scheduler.scheduler.application.impl;

import com.scheduler.scheduler.application.SignService;
import com.scheduler.scheduler.domain.User.User;
import com.scheduler.scheduler.infrastructure.config.PasswordEncoderConfiguration;
import com.scheduler.scheduler.infrastructure.config.security.JwtTokenProvider;
import com.scheduler.scheduler.infrastructure.repository.UserRepository;
import com.scheduler.scheduler.presentation.controller.SignController;
import com.scheduler.scheduler.presentation.dto.sign.SignUpRequestDto;
import com.scheduler.scheduler.presentation.dto.sign.SignUpResultDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

@SpringBootTest
@ContextConfiguration(classes = PasswordEncoderConfiguration.class)
class SignServiceImplTest {
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final JwtTokenProvider jwtTokenProvider = Mockito.mock(JwtTokenProvider.class);
    @Autowired
    private PasswordEncoder passwordEncoder;
    private SignServiceImpl signService;

    @BeforeEach
    public void setUpTest() {
        this.signService = new SignServiceImpl(userRepository, jwtTokenProvider, passwordEncoder);
    }

    @Test
    void signUp() {
        Mockito.when(userRepository.save(any(User.class)))
                .then(returnsFirstArg());

        SignUpRequestDto requestDto = SignUpRequestDto.builder()
                .id("test_id")
                .name("test_user")
                .password("test_password")
                .build();

        String pw = "test_password";
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        User user = requestDto.toUser();
        System.out.println(user);

        Assertions.assertTrue(user.passwordMatch(pw, passwordEncoder));
    }

    @Test
    void signIn() {
    }
}