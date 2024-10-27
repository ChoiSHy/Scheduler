package com.scheduler.scheduler;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        initializers = ConfigDataApplicationContextInitializer.class,
        classes = JasyptConfig.class)
public class ReadJasyptConfigTest {
    @Value("${spring.datasource.password}")
    private String decryptedData;

    @Test
    void decryption_config_test(){
        System.out.println("decryption_config: "+decryptedData);
        Assertions.assertEquals(decryptedData, "db_password");
    }
}
