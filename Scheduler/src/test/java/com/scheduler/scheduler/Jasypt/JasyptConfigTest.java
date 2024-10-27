package com.scheduler.scheduler.Jasypt;

import com.scheduler.scheduler.Jasypt.JasyptConfig;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JasyptConfig.class)
class JasyptConfigTest {
    @Autowired
    StringEncryptor jasyptStringEncryptor;
    @Test
    @DisplayName("1. Encrypt Test")
    void stringEncryptor() {
        String password = "db_password";

        String encrypted = jasyptStringEncryptor.encrypt(password);
        System.out.println("encrypted: "+encrypted);

        String decrypted = jasyptStringEncryptor.decrypt(encrypted);
        System.out.println("decrypted: "+decrypted);

        Assertions.assertEquals(decrypted, password);
    }


}