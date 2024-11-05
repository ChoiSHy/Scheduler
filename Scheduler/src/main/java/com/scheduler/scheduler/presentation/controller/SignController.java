package com.scheduler.scheduler.presentation.controller;

import com.scheduler.scheduler.application.SignService;
import com.scheduler.scheduler.domain.exception.InaccessibleException;
import com.scheduler.scheduler.presentation.dto.sign.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sign-api")
public class SignController {

    private final Logger LOGGER = LoggerFactory.getLogger(SignController.class);;
    private final SignService signService;

    @PostMapping(value = "/sign-in")
    public SignInResultDto signIn(@RequestBody SignInRequestDto requestDto) throws InaccessibleException{
        LOGGER.info("[signIn] 로그인을 시도하고 있습니다. id: {}, pw: ****", requestDto.getId());
        SignInResultDto signInResultDto = signService.signIn(requestDto);
        if (signInResultDto.getCode() == 0){
            LOGGER.info("[signIn] 정상적으로 로그인되었습니다. id: {}, token: {}", requestDto.getId(),signInResultDto.getToken());
        }
        return signInResultDto;
    }
    @PostMapping(value = "/sign-up")
    public SignUpResultDto signUp(@RequestBody SignUpRequestDto requestDto){
        SignUpResultDto resultDto = signService.signUp(requestDto);

        LOGGER.info("[signUp] 회원가입을 완료했습니다. id: {}",requestDto.getId());

        return resultDto;
    }
    @GetMapping(value = "/exception")
    public void exceptionTest() throws InaccessibleException{
        throw new InaccessibleException("접근이 금지 되었습니다.");
    }

}