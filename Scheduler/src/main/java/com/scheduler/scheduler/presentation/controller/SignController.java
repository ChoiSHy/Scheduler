package com.scheduler.scheduler.presentation.controller;

import com.scheduler.scheduler.application.SignService;
import com.scheduler.scheduler.domain.exception.InaccessibleException;
import com.scheduler.scheduler.presentation.dto.sign.*;
import com.scheduler.scheduler.presentation.dto.sign.password.PasswordChangeRequestDto;
import com.scheduler.scheduler.presentation.dto.sign.password.PasswordResponseDto;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sign/")
public class SignController {

    private final Logger LOGGER = LoggerFactory.getLogger(SignController.class);;
    private final SignService signService;

    @PostMapping(value = "/sign-in")
    public SignInResultDto signIn(@RequestBody SignInRequestDto requestDto) throws InaccessibleException{
        LOGGER.info("[signIn] 로그인을 시도하고 있습니다. id: {}, pw: ****", requestDto.getEmail());
        SignInResultDto signInResultDto = signService.signIn(requestDto);
        if (signInResultDto.getCode() == 0){
            LOGGER.info("[signIn] 정상적으로 로그인되었습니다. id: {}, token: {}", requestDto.getEmail(),signInResultDto.getToken());
        }
        return signInResultDto;
    }
    @PostMapping(value = "/sign-up")
    public SignUpResultDto signUp(@RequestBody @Valid SignUpRequestDto requestDto){
        SignUpResultDto resultDto = signService.signUp(requestDto);

        LOGGER.info("[signUp] 회원가입을 완료했습니다. id: {}",requestDto.getEmail());

        return resultDto;
    }
    @PutMapping(value = "/password")
    public ResponseEntity<PasswordResponseDto> changePassword(@RequestBody PasswordChangeRequestDto requestDto){
        PasswordResponseDto responseDto = signService.changePasswordMyself(requestDto);
        return ResponseEntity.ok().body(responseDto);
    }
    @GetMapping(value = "/password")
    public ResponseEntity<PasswordResponseDto> resetPassword(){
        PasswordResponseDto responseDto = signService.resetPasswordMyself();
        return ResponseEntity.ok().body(responseDto);
    }
    @GetMapping(value = "/exception")
    public void exceptionTest() throws InaccessibleException{
        throw new InaccessibleException("접근이 금지 되었습니다.");
    }

}
