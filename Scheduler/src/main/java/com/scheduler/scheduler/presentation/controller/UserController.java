package com.scheduler.scheduler.presentation.controller;

import com.scheduler.scheduler.application.UserService;
import com.scheduler.scheduler.presentation.dto.user.UserRequestDto;
import com.scheduler.scheduler.presentation.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController(value = "/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/user-info", method = RequestMethod.POST)
    public ResponseEntity<UserResponseDto> getUserByUid(@RequestBody UserRequestDto requestDto) {
        try {
            UserResponseDto responseDto = userService.getUserByUid(requestDto);
            return ResponseEntity.ok().body(responseDto);
        }catch (NoSuchElementException ex){
            return ResponseEntity.notFound().build();
        }

    }
}
