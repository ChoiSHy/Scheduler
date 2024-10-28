package com.scheduler.scheduler.LogBack;

import com.scheduler.scheduler.application.UserDetailService;
import com.scheduler.scheduler.domain.User.User;
import com.scheduler.scheduler.infrastructure.UserRepository;
import com.scheduler.scheduler.presentation.dto.user.request.UserCreateRequestDto;
import com.scheduler.scheduler.presentation.dto.user.response.UserCreateResponseDto;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class LogBackTest {
    private UserRepository userRepository = Mockito.mock(UserRepository.class);
    private UserDetailService userDetailService;
    private final Logger LOGGER = LoggerFactory.getLogger(LogBackTest.class);

    @BeforeEach
    public void setUp(){
        LOGGER.info("--- setUp() --- method run");
        userDetailService = new UserDetailService(userRepository);
        LOGGER.info("--- setUp() --- method done");

    }
    @Test
    @DisplayName("LogBack을 사용한 로그 기록 확인 테스트(일부러 틀린 값 체크)")
    void LogBackTest(){
        Mockito.when(userRepository.save(any(User.class)))
                .then(returnsFirstArg());
        LOGGER.info("service method - saveUser() run");
        UserCreateResponseDto savedUserDto = userDetailService.saveUser(new UserCreateRequestDto("id", "password","myName"));
        LOGGER.info("service method - saveUser() done");

        LOGGER.info("check the result");
        try {
            Assertions.assertEquals(savedUserDto.getName(), "myNa1me");
        }catch (org.opentest4j.AssertionFailedError ex ){
            LOGGER.error("결과와 예측값이 일치하지 않음");
            throw ex;
        }finally {
            LOGGER.info("TEST 종료");
        }
    }

}
