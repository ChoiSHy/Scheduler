package com.scheduler.scheduler.application.impl;

import com.scheduler.scheduler.application.UserService;
import com.scheduler.scheduler.domain.User.User;
import com.scheduler.scheduler.domain.exception.NonSignInException;
import com.scheduler.scheduler.infrastructure.repository.UserRepository;
import com.scheduler.scheduler.presentation.dto.user.modify.AdminUserInfoModifyRequestDto;
import com.scheduler.scheduler.presentation.dto.user.modify.MyUserInfoModifyRequestDto;
import com.scheduler.scheduler.presentation.dto.user.UserInfoRequestDto;
import com.scheduler.scheduler.presentation.dto.user.UserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserDetails loadUserByUsername(String username) {
        LOGGER.info("[loadUserByUsername] loadUserByUsername 수행. id: {}", username);
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다."));
    }

    // 현재 로그인 된 계정의 정보 가져오기
    private User getUserFromContext() throws NoSuchElementException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null|| authentication instanceof AnonymousAuthenticationToken)
            throw new NonSignInException();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> {
            LOGGER.info("[getUserFromContext] : cannot find user");
            throw new NoSuchElementException();
        });
    }

    @Override
    // 관리자가 이메일로 사용자 정보 접근
    public UserInfoResponseDto getUserByEmail(UserInfoRequestDto requestDto) {
        LOGGER.info("[getUserByEmail] : ** START ** id: {}", requestDto.getEmail());

        User foundUser = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> {
                    LOGGER.error("[getUserByEmail] : ** cannot find that user **");
                    throw new NoSuchElementException("회원 정보를 찾을 수 없습니다.");
                });

        LOGGER.info("[getUserByEmail] : finding success - " + foundUser);

        UserInfoResponseDto responseDto = foundUser.toResponseDto();
        LOGGER.info("[getUserByEmail] : User -> UserResponseDto");

        LOGGER.info("[getUserByEmail] : ** DONE **");
        return responseDto;
    }
    @Override
    public UserInfoResponseDto modifyUserInfoByEmail(AdminUserInfoModifyRequestDto requestDto) {
        LOGGER.info("[modifyUserInfoByEmail] : -- START --");

        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(NoSuchElementException::new);
        user.modify(requestDto);

        User savedUser = userRepository.save(user);
        UserInfoResponseDto responseDto = savedUser.toResponseDto();
        LOGGER.info("[modifyUserInfoByEmail] : modified user = {}",responseDto);

        LOGGER.info("[modifyUserInfoByEmail] : -- DONE --");
        return responseDto;
    }

    @Override
    public void removeUserByEmail(UserInfoRequestDto requestDto) {
        LOGGER.info("[removeUserById] : -- START --");
        User user = userRepository.findByEmail(requestDto.getEmail())
                        .orElseThrow(NoSuchElementException::new);
        LOGGER.info("[removeUserById] : user = {}", user);
        userRepository.delete(user);
        LOGGER.info("[removeUserById] : delete complete");
        LOGGER.info("[removeUserById] : -- DONE --");
    }
/* my-info */
    @Override
    // 자신의 사용자 정보 접근
    public UserInfoResponseDto getMyUserInfo() throws NoSuchElementException {
        LOGGER.info("[getMyUserInfo] : -- START --");
        User user = getUserFromContext();
        LOGGER.info("[getMyUserInfo] : Current User Email: {}", user.getEmail());

        UserInfoResponseDto responseDto = user.toResponseDto();
        LOGGER.info("[getMyUserInfo] : -- DONE --");
        return responseDto;
    }

    @Override
    // 사용자 정보 수정
    public UserInfoResponseDto modifyMyUserInfo(MyUserInfoModifyRequestDto requestDto) {
        LOGGER.info("[modifyMyUserInfo] : -- START --");
        User user = getUserFromContext();
        LOGGER.info("[modifyMyUserInfo] : Current User Email: {}", user.getEmail());

        user.modify(requestDto);
        User savedUser = userRepository.save(user);
        UserInfoResponseDto responseDto = savedUser.toResponseDto();
        LOGGER.info("[modifyMyUserInfo] : savedUser = {}", responseDto);
        LOGGER.info("[modifyMyUserInfo] : -- DONE --");
        return responseDto;
    }

    @Override
    public void removeMyself() {
        LOGGER.info("[removeMyself] : -- START --");
        User user = getUserFromContext();
        userRepository.delete(user);
        SecurityContextHolder.clearContext();
        LOGGER.info("[removeMyself] : sign out");
        LOGGER.info("[removeMyself] : -- DONE --");
    }
}
