package com.scheduler.scheduler.presentation.controller;

import com.scheduler.scheduler.application.UserService;
import com.scheduler.scheduler.domain.exception.NonSignInException;
import com.scheduler.scheduler.presentation.dto.user.UserModifyRequestDto;
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
@RequestMapping(value = "/api/user")
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
        try {
            UserResponseDto responseDto = userService.getUserByEmail(requestDto);
            return ResponseEntity.ok().body(responseDto);
        }catch (NoSuchElementException ex){
            LOGGER.error("[getUserInfoByEmail - controller] : NOT-FOUND");
            return ResponseEntity.notFound().build();
        }
    }
    /**
     * TODO: 1. 관리자 기준 수정
     * TODO: 2. 관라자 기준 삭제
     * TODO: 3. my-info 기능들 테스트(exception 포함)
     * **/
    /* my-info */
    /* 조회 */
    @RequestMapping(value = "/user-info/my-info", method = RequestMethod.GET)
    @Parameter(
            name="X-AUTH-TOKEN",
            description = "로그인 후 발급 받은 access_token",
            required = true,
            in=ParameterIn.HEADER
    )
    public ResponseEntity<UserResponseDto> getMyUserInfo(){
        UserResponseDto responseDto = userService.getMyUserInfo();
        return ResponseEntity.ok(responseDto);
    }
    /* 수정 */
    @RequestMapping(value = "/user-info/my-info/modify", method = RequestMethod.PUT)
    @Parameter(
            name = "X-AUTH-TOKEN",
            description = "로그인 후 발급 받은 access_token",
            required = true,
            in = ParameterIn.HEADER
    )
    public ResponseEntity<UserResponseDto> modifyUserMyself(UserModifyRequestDto requestDto){
        try {
            UserResponseDto responseDto = userService.modifyMyUserInfo(requestDto);
            return ResponseEntity.ok(responseDto);
        }catch (NonSignInException e){
            return ResponseEntity.badRequest().build();
        }
    }
    /* 삭제 */
    @RequestMapping(value = "/user-info/my-info/delete", method = RequestMethod.DELETE)
    @Parameter(
            name = "X-AUTH-TOKEN",
            description = "로그인 후 발급 받은 access_token",
            required = true,
            in = ParameterIn.HEADER
    )
    public ResponseEntity<String> deleteUserMyself(){
        userService.removeMyself();
        return ResponseEntity.ok().body("회원정보 삭제 완료");
    }

}
