package com.scheduler.scheduler;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JasyptConfigAESTest {
    static StandardPBEStringEncryptor enc;
    @Test
    @DisplayName("1. Encrypt Test")
    void stringEncryptor() {
        String url = "db_url";
        String username = "db_username";
        String password = "1234";

        System.out.println(enc.encrypt(url));
        System.out.println(enc.encrypt(username));
        System.out.println(enc.encrypt(password));

        System.out.println(decrypt_text(url));
        System.out.println(decrypt_text(username));
        System.out.println(decrypt_text(password));

    }
    String decrypt_text(String value){
        return enc.decrypt("ENC("+value+")");
    }
    @BeforeAll
    static void setting(){
        String key = "sample";
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        pbeEnc.setPassword(key);
        enc = pbeEnc;
    }
}