package com.scheduler.scheduler.presentation.controller;

import com.scheduler.scheduler.application.UserService;
import com.scheduler.scheduler.presentation.dto.user.request.UserCreateRequestDto;
import com.scheduler.scheduler.presentation.dto.user.response.UserCreateResponseDto;
import com.scheduler.scheduler.presentation.dto.user.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController(value = "/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userDetailService;
    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public UserCreateResponseDto register(@RequestBody UserCreateRequestDto requestDto){
        UserCreateResponseDto responseDto = userDetailService.saveUser(requestDto);
        return responseDto;
    }
    @RequestMapping(value = "/user-info/{id}", method = RequestMethod.GET)
    public UserResponseDto getUserById(@PathVariable Long id){
        return null;
    }
}
