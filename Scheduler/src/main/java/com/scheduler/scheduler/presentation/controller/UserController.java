package com.scheduler.scheduler.presentation.controller;

import com.scheduler.scheduler.application.UserService;
import com.scheduler.scheduler.presentation.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController(value = "/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userDetailService;
    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/user-info/{id}", method = RequestMethod.GET)
    public UserResponseDto getUserById(@PathVariable Long id){
        return null;
    }
}
