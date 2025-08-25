package com.avrist.webui.global.util;

import com.github.echological.sc.global.util.AESEncryptionUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class AESEncryptionUtilTest {

    @SneakyThrows
    @Test
    void testValidation(){
        var password = "Avrist123";
        var encryptedPassword = AESEncryptionUtil.encrypt(password);
        var decryptedPassword = AESEncryptionUtil.decrypt(encryptedPassword);

        Assertions.assertEquals(password, decryptedPassword);
    }

}