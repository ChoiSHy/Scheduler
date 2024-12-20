package com.scheduler.scheduler.presentation.controller;

import com.scheduler.scheduler.application.UserService;
import com.scheduler.scheduler.presentation.dto.user.UserRequestDto;
import com.scheduler.scheduler.presentation.dto.user.UserResponseDto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/user-info", method = RequestMethod.POST)
    @Parameter(
            name = "X-AUTH-TOKEN",
            description = "로그인 후 발급 받은 access_token",
            required = true,
            in = ParameterIn.HEADER
    )
    public ResponseEntity<UserResponseDto> getUserInfoByEmail(@RequestBody UserRequestDto requestDto) {
        LOGGER.info("[getUserInfoByEmail - controller] : START");
        try {
            UserResponseDto responseDto = userService.getUserByEmail(requestDto);
            LOGGER.info("[getUserInfoByEmail - controller] : DONE");
            return ResponseEntity.ok().body(responseDto);
        }catch (NoSuchElementException ex){
            LOGGER.error("[getUserInfoByEmail - controller] : NOT-FOUND");
            return ResponseEntity.notFound().build();
        }
    }
    @RequestMapping(value = "/user-info/my-info", method = RequestMethod.GET)
    public ResponseEntity<UserResponseDto> getMyUserInfo(){
        LOGGER.info("[getMyUserInfo - controller]");
        UserResponseDto responseDto = userService.getUser();
        return ResponseEntity.ok(responseDto);
    }
    @RequestMapping(value = "/user-info/modify", method = RequestMethod.PUT)
    public ResponseEntity<UserResponseDto> modifyUser(){
        return null;
    }
    @RequestMapping(value = "/user-info/modify/password", method = RequestMethod.PUT)
    public ResponseEntity<UserResponseDto> modifyPassword(){
        return null;
    }

}
