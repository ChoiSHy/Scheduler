package com.scheduler.scheduler.presentation.controller;

import com.scheduler.scheduler.application.UserDetailService;
import com.scheduler.scheduler.presentation.dto.user.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserDetailService userDetailService;
    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/user/user-info/{id}")
    public UserResponseDto getUserById(@PathVariable Long id){
        return null;
    }
}
