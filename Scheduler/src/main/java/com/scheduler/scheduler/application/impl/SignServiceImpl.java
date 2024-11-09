package com.scheduler.scheduler.application.impl;

import com.scheduler.scheduler.application.SignService;
import com.scheduler.scheduler.domain.User.User;
import com.scheduler.scheduler.domain.exception.IncorrectSignInException;
import com.scheduler.scheduler.infrastructure.config.security.CommonResponse;
import com.scheduler.scheduler.infrastructure.config.security.JwtTokenProvider;
import com.scheduler.scheduler.infrastructure.repository.UserRepository;
import com.scheduler.scheduler.presentation.dto.sign.SignInRequestDto;
import com.scheduler.scheduler.presentation.dto.sign.SignInResultDto;
import com.scheduler.scheduler.presentation.dto.sign.SignUpRequestDto;
import com.scheduler.scheduler.presentation.dto.sign.SignUpResultDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {
    private final Logger LOGGER = LoggerFactory.getLogger(SignServiceImpl.class);

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public SignUpResultDto signUp(SignUpRequestDto requestDto) {
        LOGGER.info("[signUp] 회원 가입 정보 전달");
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        User user = requestDto.toUser();
        LOGGER.info("[signUp] user: {}", user);

        User savedUser = userRepository.save(user);
        SignUpResultDto signUpResultDto = new SignUpResultDto();

        LOGGER.info("[signUp] userEntity 값이 들어왔는지 확인 후 결과값 주입");
        if (!savedUser.getName().isEmpty()) {
            LOGGER.info("[signUp] 정상 처리 완료");
            setSuccessResult(signUpResultDto);
        } else {
            LOGGER.info("[signUp] 실패 처리 완료");
            setFailResult(signUpResultDto);
        }
        return signUpResultDto;
    }

    @Override
    public SignInResultDto signIn(SignInRequestDto requestDto) throws RuntimeException {
        String id = requestDto.getId();
        String password = requestDto.getPassword();
        User user = null;
        LOGGER.info("[signIn] signDataHandler 로 회원 정보 요청. id: {}", id);


        user = userRepository.findByUid(id)
                .orElseThrow(()->{
                    LOGGER.error("[signIn] 해당 유저 정보를 찾을 수 없습니다.");
                    throw new NoSuchElementException("해당 유저 정보를 찾을 수 없습니다.");});

        LOGGER.info("[signIn] user: {}", user);

        LOGGER.info("[signIn] 패스워드 비교 수행");
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IncorrectSignInException();
        }
        LOGGER.info("[signIn] 패스워드 일치");

        LOGGER.info("[signIn] SignInResultDto 객체 생성");
        SignInResultDto signInResultDto = SignInResultDto.builder()
                .token(jwtTokenProvider.createToken(
                        String.valueOf(user.getUid()),
                        user.getRole()))
                .build();

        LOGGER.info("[getSignInResult] SignInResultDto 객체에 값 주입");
        setSuccessResult(signInResultDto);

        return signInResultDto;
    }

    private void setSuccessResult(SignUpResultDto result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMessage(CommonResponse.SUCCESS.getMsg());
    }

    private void setFailResult(SignUpResultDto result) {
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMessage(CommonResponse.FAIL.getMsg());
    }
}
