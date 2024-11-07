package com.scheduler.scheduler;

import com.scheduler.scheduler.infrastructure.config.PasswordEncoderConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
@SpringBootTest
@ContextConfiguration(classes = PasswordEncoderConfiguration.class)
public class PasswordEncoderTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void pwdEc(){
        String pwd = "kedric123";
        String encodedPwd = passwordEncoder.encode(pwd); //암호화 하는부분
        System.out.println(encodedPwd);

        Assertions.assertTrue(passwordEncoder.matches("kedric123",encodedPwd));
    }

}
