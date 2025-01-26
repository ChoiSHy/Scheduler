package com.scheduler.scheduler.application.impl;

import com.scheduler.scheduler.application.UserService;
import com.scheduler.scheduler.domain.User.User;
import com.scheduler.scheduler.infrastructure.repository.UserRepository;
import com.scheduler.scheduler.presentation.dto.user.UserModifyRequestDto;
import com.scheduler.scheduler.presentation.dto.user.UserRequestDto;
import com.scheduler.scheduler.presentation.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

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
    private User getUserFromContext() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> {
            LOGGER.info("[getUserFromContext] : cannot find user");
            throw new NoSuchElementException();
        });
    }

    @Override
    // 관리자가 이메일로 사용자 정보 접근
    public UserResponseDto getUserByEmail(UserRequestDto requestDto) {
        LOGGER.info("[UserDetailService - getUserByUid] : ** START ** id: {}", requestDto.getEmail());

        User foundUser = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> {
                    LOGGER.error("[UserDetailService - getUserByUid] : ** cannot find that user **");
                    throw new NoSuchElementException("회원 정보를 찾을 수 없습니다.");
                });

        LOGGER.info("[UserDetailService - getUserByUid] : finding success - " + foundUser);

        UserResponseDto responseDto = foundUser.toResponseDto();
        LOGGER.info("[UserDetailService - getUserByUid] : User -> UserResponseDto");

        LOGGER.info("[UserDetailService - getUserByUid] : ** DONE **");
        return responseDto;
    }

    @Override
    // 자신의 사용자 정보 접근
    public UserResponseDto getMyUserInfo() throws NoSuchElementException {
        LOGGER.info("[getMyUserInfo] : -- START --");
        User user = getUserFromContext();
        LOGGER.info("[getMyUserInfo] : Current User Email: {}", user.getEmail());

        UserResponseDto responseDto = user.toResponseDto();
        LOGGER.info("[getMyUserInfo] : -- DONE --");
        return responseDto;
    }

    @Override
    // 사용자 정보 수정
    public UserResponseDto modifyMyUserInfo(UserModifyRequestDto requestDto) {
        LOGGER.info("[modifyMyUserInfo] : -- START --");
        User user = getUserFromContext();
        LOGGER.info("[modifyMyUserInfo] : Current User Email: {}", user.getEmail());

        user.modify(requestDto);
        User savedUser = userRepository.save(user);
        UserResponseDto responseDto = savedUser.toResponseDto();
        LOGGER.info("[modifyMyUserInfo] : savedUser = {}", responseDto);
        LOGGER.info("[modifyMyUserInfo] : -- DONE --");
        return responseDto;
    }

    @Override
    public void removeUserById(Long id) {
        LOGGER.info("[removeUserById] : -- START --");
        userRepository.deleteById(id);
        LOGGER.info("[removeUserById] : -- DONE --");
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
