package com.github.echological.sc.service.auth.basicauthvalidation;

import com.github.echological.sc.datasource.auth.entity.AuthUserEntity;
import com.github.echological.sc.datasource.auth.repository.AuthUserRepository;
import com.github.echological.sc.global.constant.ServiceStatus;
import com.github.echological.sc.global.exception.BusinessServiceValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class BasicAuthValidationServiceTest {

    @Mock
    private AuthUserRepository authUserRepository;

    @InjectMocks
    private BasicAuthValidationService basicAuthValidationService;

    private String generateBasicAuthToken(String username, String password){
        String credentials = String.format("%s:%s", username, password);
        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        return encoded;
    }


    private String hashPwd(String pwd){
        return BCrypt.hashpw(pwd, BCrypt.gensalt(12));
    }

    @Test
    @DisplayName("Normal test")
    void test1(){
        var token = String.format("Basic %s", generateBasicAuthToken("test", "test123"));
        given(authUserRepository.findByUsername(any())).willReturn(Optional.of(AuthUserEntity.builder()
                        .id(UUID.randomUUID().toString())
                        .username("test")
                        .password(hashPwd("test123"))
                        .enabled(true)
                        .accountNonExpired(true)
                        .accountNonLocked(true)
                        .credentialsNonExpired(true)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                .build()));
        var result = basicAuthValidationService.execute(token, "en");
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("Invalid Password")
    void test2(){
        var token = String.format("Basic %s", generateBasicAuthToken("test", "test124"));
        given(authUserRepository.findByUsername(any())).willReturn(Optional.of(AuthUserEntity.builder()
                .id(UUID.randomUUID().toString())
                .username("test")
                .password(hashPwd("test123"))
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build()));
        var result = Assertions.assertThrows(BusinessServiceValidationException.class, () -> basicAuthValidationService.execute(token, "en"));

        Assertions.assertTrue(result.getResponseCode().equalsIgnoreCase(ServiceStatus.UNAUTHORIZED.getCode()));
    }

    @Test
    @DisplayName("Invalid Username")
    void test3(){
        var token = String.format("Basic %s", generateBasicAuthToken("test_username", "test124"));
        given(authUserRepository.findByUsername(any())).willReturn(Optional.of(AuthUserEntity.builder()
                .id(UUID.randomUUID().toString())
                .username("test")
                .password(hashPwd("test123"))
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build()));
        var result = Assertions.assertThrows(BusinessServiceValidationException.class, () -> basicAuthValidationService.execute(token, "en"));

        Assertions.assertTrue(result.getResponseCode().equalsIgnoreCase(ServiceStatus.UNAUTHORIZED.getCode()));
    }

    @Test
    @DisplayName("No Header")
    void test4(){
        String token = null;
        var result = Assertions.assertThrows(BusinessServiceValidationException.class, () -> basicAuthValidationService.execute(token, "en"));

        Assertions.assertTrue(result.getResponseCode().equalsIgnoreCase(ServiceStatus.UNAUTHORIZED.getCode()));
    }

    @Test
    @DisplayName("No Invalid Format username:password")
    void test5(){
        var token = String.format("Basic %s",  Base64.getEncoder().encodeToString("HelloWorld".getBytes(StandardCharsets.UTF_8)));
        var result = Assertions.assertThrows(BusinessServiceValidationException.class, () -> basicAuthValidationService.execute(token, "en"));

        Assertions.assertTrue(result.getResponseCode().equalsIgnoreCase(ServiceStatus.UNAUTHORIZED.getCode()));
    }


}