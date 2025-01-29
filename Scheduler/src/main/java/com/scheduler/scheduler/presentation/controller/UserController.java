package com.scheduler.scheduler.presentation.controller;

import com.scheduler.scheduler.application.UserService;
import com.scheduler.scheduler.domain.exception.NonSignInException;
import com.scheduler.scheduler.presentation.dto.user.UserInfoRequestDto;
import com.scheduler.scheduler.presentation.dto.user.UserInfoResponseDto;
import com.scheduler.scheduler.presentation.dto.user.modify.AdminUserInfoModifyRequestDto;
import com.scheduler.scheduler.presentation.dto.user.modify.MyUserInfoModifyRequestDto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
/**
 * TODO: 1. 수정 기능 중 비밀번호 변경 기능은 따로 분리
 * TODO: 2. my-info 기능들 테스트(exception 포함)
 * TODO: 3. admin 기능 test 필요
 * TODO: 4. ExceptionHandler 가 이미 존재하는데 따로 try/catch 문을 유지해야하는지.
*/
@RestController
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /* admin 기능 */
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
        } catch (NoSuchElementException ex) {
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
    public ResponseEntity<UserInfoResponseDto> modifyUserInfo(@RequestBody AdminUserInfoModifyRequestDto requestDto) {
        try {
            UserInfoResponseDto responseDto = userService.modifyUserInfoByEmail(requestDto);
            return ResponseEntity.ok().body(responseDto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/user-info", method = RequestMethod.DELETE)
    @Parameter(
            name = "X-AUTH-TOKEN",
            description = "로그인 후 발급 받은 access_token",
            required = true,
            in = ParameterIn.HEADER
    )
    public ResponseEntity<String> deleteUserInfo(@RequestBody UserInfoRequestDto requestDto) {
        try {
            userService.removeUserByEmail(requestDto);
            return ResponseEntity.ok().body(requestDto.getEmail() + " 계정 삭제 완료");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /* my-info
     * */
    /* 조회 */
    @RequestMapping(value = "/user-info/my-info", method = RequestMethod.GET)
    @Parameter(
            name = "X-AUTH-TOKEN",
            description = "로그인 후 발급 받은 access_token",
            required = true,
            in = ParameterIn.HEADER
    )
    public ResponseEntity<UserInfoResponseDto> getMyUserInfo() {
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
    public ResponseEntity<UserInfoResponseDto> modifyUserMyself(MyUserInfoModifyRequestDto requestDto) {
        try {
            UserInfoResponseDto responseDto = userService.modifyMyUserInfo(requestDto);
            return ResponseEntity.ok(responseDto);
        } catch (NonSignInException e) {
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
    public ResponseEntity<String> deleteUserMyself() {
        userService.removeMyself();
        return ResponseEntity.ok().body("회원정보 삭제 완료");
    }

}
