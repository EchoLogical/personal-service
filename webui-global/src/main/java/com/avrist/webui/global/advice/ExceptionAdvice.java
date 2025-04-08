package com.avrist.webui.global.advice;

import com.avrist.core.constant.AVRStatus;
import com.avrist.webui.global.exception.BusinessServiceValidationException;
import com.avrist.webui.global.model.BaseResponse;
import com.avrist.webui.global.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ExceptionAdvice.class);

    private static final String LOG_ERROR_MSG = "requestId {} error: {}";
    private static final String LOG_STACKTRACE_MSG = "requestId {} stacktrace: {}";

    @Value("${spring.application.name}")
    private String appName;

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String requestId = UUID.randomUUID().toString();
        log.error(LOG_ERROR_MSG, requestId, ex.getMessage());
        log.error(LOG_STACKTRACE_MSG, requestId, ex.getStackTrace());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.builder()
                .responseCode(AVRStatus.UNSUPPORTED_MEDIA_TYPE.getCode())
                .responseMessage(AVRStatus.UNSUPPORTED_MEDIA_TYPE.getStatus())
                .requestId(requestId)
                .error(Stream.of("Unsupported media type.").collect(Collectors.toList()))
                .requestTimestamp(new Date().getTime())
                .build());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String requestId = UUID.randomUUID().toString();
        log.error(LOG_ERROR_MSG, requestId, ex.getMessage());
        log.error(LOG_STACKTRACE_MSG, requestId, ex.getStackTrace());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.builder()
                .responseCode(AVRStatus.NO_HANDLER_FOUND.getCode())
                .responseMessage(AVRStatus.NO_HANDLER_FOUND.getStatus())
                .requestId(requestId)
                .error(Stream.of("No handler found for this path.").collect(Collectors.toList()))
                .requestTimestamp(new Date().getTime())
                .build());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String requestId = UUID.randomUUID().toString();
        log.error(LOG_ERROR_MSG, requestId, ex.getMessage());
        log.error(LOG_STACKTRACE_MSG, requestId, ex.getStackTrace());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.builder()
                .responseCode(AVRStatus.MESSAGE_NOT_READABLE.getCode())
                .responseMessage(AVRStatus.MESSAGE_NOT_READABLE.getStatus())
                .requestId(requestId)
                .error(Stream.of("Message not readable").collect(Collectors.toList()))
                .requestTimestamp(new Date().getTime())
                .build());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String requestId = UUID.randomUUID().toString();

        List<Map<String, String>> errorList = new ArrayList<>();
        for(FieldError x:ex.getFieldErrors()){
            Map<String, String> error = new HashMap<>();
            error.put(x.getField(), x.getDefaultMessage());
            errorList.add(error);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.builder()
                .responseCode(AVRStatus.INVALID_ARGUMENT.getCode())
                .responseMessage(AVRStatus.INVALID_ARGUMENT.getStatus())
                .requestId(requestId)
                .requestTimestamp(new Date().getTime())
                .error(errorList)
                .build());
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<BaseResponse<Object>> handleRuntimeException(RuntimeException ex) {
        String requestId = UUID.randomUUID().toString();
        log.error(LOG_ERROR_MSG, requestId, ex.getMessage());
        log.error(LOG_STACKTRACE_MSG, requestId, ex.getStackTrace());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponse.builder()
                .responseCode(AVRStatus.ERROR.getCode())
                .responseMessage(AVRStatus.ERROR.getStatus())
                .requestId(requestId)
                .requestTimestamp(new Date().getTime())
                .build());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<BaseResponse<Object>> handleGenericException(Exception ex) {
        String requestId = UUID.randomUUID().toString();
        log.error(LOG_ERROR_MSG, requestId, ex.getMessage());
        log.error(LOG_STACKTRACE_MSG, requestId, ex.getStackTrace());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponse.builder()
                .responseCode(AVRStatus.ERROR.getCode())
                .responseMessage(AVRStatus.ERROR.getStatus())
                .requestId(requestId)
                .requestTimestamp(new Date().getTime())
                .build());
    }

    @ExceptionHandler(BusinessServiceValidationException.class)
    public final ResponseEntity<BaseResponse<Object>> handleBusinessValidationException(BusinessServiceValidationException ex, WebRequest request, HttpServletResponse response) throws IOException {
        String requestId = UUID.randomUUID().toString();
        String userAgent = request.getHeader("User-Agent");

        log.error(LOG_ERROR_MSG, requestId, ex.getMessage());
        log.error(LOG_STACKTRACE_MSG, requestId, ex.getStackTrace());
        log.error("User-agent: {}", userAgent);

        if (WebUtil.isBrowser(userAgent) &&
                AVRStatus.UNAUTHORIZED_REDIRECT.getCode().equalsIgnoreCase(ex.getResponseCode())) {
            response.sendRedirect("/login");
            return null;
        }

        return ResponseEntity.status(ex.getHttpStatus()).body(BaseResponse.builder()
                .responseCode(ex.getResponseCode())
                .responseMessage(ex.getResponseMessage())
                .error(ex.getError())
                .requestId(requestId)
                .requestTimestamp(new Date().getTime())
                .content(null)
                .build());
    }

}
