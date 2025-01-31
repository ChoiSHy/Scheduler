package com.scheduler.scheduler.application.impl;

import com.scheduler.scheduler.application.SignService;
import com.scheduler.scheduler.domain.User.User;
import com.scheduler.scheduler.domain.exception.IncorrectSignInException;
import com.scheduler.scheduler.infrastructure.config.security.CommonResponse;
import com.scheduler.scheduler.infrastructure.config.security.JwtTokenProvider;
import com.scheduler.scheduler.infrastructure.repository.UserRepository;
import com.scheduler.scheduler.presentation.dto.sign.*;
import com.scheduler.scheduler.presentation.dto.sign.password.PasswordChangeRequestDto;
import com.scheduler.scheduler.presentation.dto.sign.password.PasswordResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {
    private final Logger LOGGER = LoggerFactory.getLogger(SignServiceImpl.class);

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private static final char[] rndAllCharacters = new char[]{
            //number
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            //uppercase
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            //lowercase
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            //special symbols
            '@', '$', '!', '%', '*', '?', '&'
    };

    public SignUpResultDto signUp(SignUpRequestDto requestDto) {
        LOGGER.info("[signUp] email 중복여부 확인");
        
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
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        User user = null;
        LOGGER.info("[signIn] signDataHandler 로 회원 정보 요청. email: {}", email);


        user = userRepository.findByEmail(email)
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
                        String.valueOf(user.getEmail()),
                        user.getRole()))
                .build();

        LOGGER.info("[getSignInResult] SignInResultDto 객체에 값 주입");
        setSuccessResult(signInResultDto);

        return signInResultDto;
    }

    @Override
    public PasswordResponseDto changePasswordMyself(PasswordChangeRequestDto requestDto) {
        return null;
    }

    @Override
    public PasswordResponseDto resetPasswordMyself() {
        /*
         * TODO: 1. 수정 기능 중 비밀번호 변경 기능은 따로 분리
         * TODO: 1.1. 비밀번호 로직 변경 고민
        * */
        return null;
    }
    private String getRandomPassword(int length){
        SecureRandom random = new SecureRandom();

        int rndAllCharsLength = rndAllCharacters.length;
        while(true) {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < length; i++) {
                sb.append(rndAllCharacters[random.nextInt(rndAllCharsLength)]);
            }
            String randomPassword = sb.toString();

            String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}";

            if (Pattern.matches(pattern, randomPassword))
                return randomPassword;
        }

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
