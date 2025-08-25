package com.github.echological.sc.service.auth.apikeyvalidation;

import com.github.echological.sc.global.constant.ServiceStatus;
import com.github.echological.sc.global.exception.BusinessServiceValidationException;
import com.github.echological.sc.service.auth.apikeyvalidation.helper.ApiKeyValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class ApiKeyValidationServiceTest {

    @Mock
    private ApiKeyValidator apiKeyValidator;

    @InjectMocks
    private ApiKeyValidationService service;

    @Test
    void shouldThrowInvalidArgumentWhenApiKeyNull() {
        // given
        String apiKey = null;

        // when
        BusinessServiceValidationException ex = assertThrows(
                BusinessServiceValidationException.class,
                () -> service.execute(apiKey)
        );

        // then
        assertEquals(ServiceStatus.INVALID_ARGUMENT.getCode(), ex.getResponseCode());
        assertEquals(ServiceStatus.INVALID_ARGUMENT.getStatus(), ex.getResponseMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getHttpStatus());
        assertTrue(ex.getError().get(0).contains("API key must be provided"));
        verifyNoInteractions(apiKeyValidator);
    }

    @Test
    void shouldThrowInvalidArgumentWhenApiKeyBlank() {
        // given
        String apiKey = "   ";

        // when
        BusinessServiceValidationException ex = assertThrows(
                BusinessServiceValidationException.class,
                () -> service.execute(apiKey)
        );

        // then
        assertEquals(ServiceStatus.INVALID_ARGUMENT.getCode(), ex.getResponseCode());
        assertEquals(ServiceStatus.INVALID_ARGUMENT.getStatus(), ex.getResponseMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getHttpStatus());
        assertTrue(ex.getError().get(0).contains("API key must be provided"));
        verifyNoInteractions(apiKeyValidator);
    }

    @Test
    void shouldThrowUnauthorizedWhenApiKeyInvalid() throws Exception {
        // given
        String apiKey = "invalid-key";
        given(apiKeyValidator.isValid(apiKey)).willReturn(false);

        // when
        BusinessServiceValidationException ex = assertThrows(
                BusinessServiceValidationException.class,
                () -> service.execute(apiKey)
        );

        // then
        assertEquals(ServiceStatus.UNAUTHORIZED.getCode(), ex.getResponseCode());
        assertEquals(ServiceStatus.UNAUTHORIZED.getStatus(), ex.getResponseMessage());
        assertEquals(HttpStatus.UNAUTHORIZED, ex.getHttpStatus());
        assertTrue(ex.getResponseMessage().contains("Invalid or expired API key"));
        then(apiKeyValidator).should(times(1)).isValid(apiKey);
        then(apiKeyValidator).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldReturnTrueWhenApiKeyValid() throws Exception {
        // given
        String apiKey = "valid-key";
        given(apiKeyValidator.isValid(apiKey)).willReturn(true);

        // when
        Boolean result = service.execute(apiKey);

        // then
        assertTrue(result);
        then(apiKeyValidator).should(times(1)).isValid(apiKey);
        then(apiKeyValidator).shouldHaveNoMoreInteractions();
    }
}


