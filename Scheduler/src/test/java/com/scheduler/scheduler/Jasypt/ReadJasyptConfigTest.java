package com.scheduler.scheduler.Jasypt;

import com.scheduler.scheduler.infrastructure.config.Jasypt.JasyptConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        initializers = ConfigDataApplicationContextInitializer.class,
        classes = JasyptConfig.class)
public class ReadJasyptConfigTest {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Test
    @DisplayName("db정보 복호화 확인")
    void decryption_config_test(){
        System.out.println("url: "+url);
        System.out.println("username: "+username);
        System.out.println("password: "+password);

        Assertions.assertEquals(username, "root");
    }
}
