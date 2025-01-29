package com.scheduler.scheduler.presentation.controller;

import com.scheduler.scheduler.application.UserService;
import com.scheduler.scheduler.domain.exception.NonSignInException;
import com.scheduler.scheduler.presentation.dto.user.UserInfoRequestDto;
import com.scheduler.scheduler.presentation.dto.user.UserInfoResponseDto;
import com.scheduler.scheduler.presentation.dto.user.modify.MyUserInfoModifyRequestDto;
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

    /* admin 기능
    * TODO: 4. admin 기능 test 필요
    * */
    @RequestMapping(value = "/user-info", method = RequestMethod.POST)
    @Parameter(
            name = "X-AUTH-TOKEN",
            description = "로그인 후 발급 받은 access_token",
            required = true,
            in = ParameterIn.HEADER
    )
    public ResponseEntity<UserInfoResponseDto> getUserInfoByEmail(@RequestBody UserInfoRequestDto requestDto) {
        try {
            UserInfoResponseDto responseDto = userService.getUserByEmail(requestDto);
            return ResponseEntity.ok().body(responseDto);
        }catch (NoSuchElementException ex){
            LOGGER.error("[getUserInfoByEmail - controller] : NOT-FOUND");
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/user-info", method = RequestMethod.PUT)
    @Parameter(
            name = "X-AUTH-TOKEN",
            description = "로그인 후 발급 받은 access_token",
            required = true,
            in = ParameterIn.HEADER
    )
    public ResponseEntity<UserInfoResponseDto> modifyUserInfo(@RequestBody UserInfoRequestDto requestDto){
        //TODO: 1. 관리자 기준 수정

        return null;
    }
    @RequestMapping(value = "/user-info", method = RequestMethod.DELETE)
    @Parameter(
            name = "X-AUTH-TOKEN",
            description = "로그인 후 발급 받은 access_token",
            required = true,
            in = ParameterIn.HEADER
    )
    public ResponseEntity<UserInfoResponseDto> deleteUserInfo(@RequestBody UserInfoRequestDto requestDto){
        // TODO: 2. 관라자 기준 삭제

        return null;
    }
    /* my-info
    * TODO: 3. my-info 기능들 테스트(exception 포함)
    * */
    /* 조회 */
    @RequestMapping(value = "/user-info/my-info", method = RequestMethod.GET)
    @Parameter(
            name="X-AUTH-TOKEN",
            description = "로그인 후 발급 받은 access_token",
            required = true,
            in=ParameterIn.HEADER
    )
    public ResponseEntity<UserInfoResponseDto> getMyUserInfo(){
        UserInfoResponseDto responseDto = userService.getMyUserInfo();
        return ResponseEntity.ok(responseDto);
    }
    /* 수정 */
    @RequestMapping(value = "/user-info/my-info", method = RequestMethod.PUT)
    @Parameter(
            name = "X-AUTH-TOKEN",
            description = "로그인 후 발급 받은 access_token",
            required = true,
            in = ParameterIn.HEADER
    )
    public ResponseEntity<UserInfoResponseDto> modifyUserMyself(MyUserInfoModifyRequestDto requestDto){
        try {
            UserInfoResponseDto responseDto = userService.modifyMyUserInfo(requestDto);
            return ResponseEntity.ok(responseDto);
        }catch (NonSignInException e){
            return ResponseEntity.badRequest().build();
        }
    }
    /* 삭제 */
    @RequestMapping(value = "/user-info/my-info", method = RequestMethod.DELETE)
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
