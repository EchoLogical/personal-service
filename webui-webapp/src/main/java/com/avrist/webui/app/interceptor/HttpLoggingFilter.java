package com.avrist.webui.app.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class HttpLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(HttpLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
        // Log the request
        logRequest(wrappedRequest);
        filterChain.doFilter(wrappedRequest, wrappedResponse);
        // Log the response
        logResponse(wrappedResponse);
    }

    private void logRequest(ContentCachingRequestWrapper request) throws IOException {
        String contentType = request.getContentType();
        if (contentType != null && contentType.contains("application/json")) {
            String requestBody = new String(request.getContentAsByteArray());
            logger.info("Request: method={}, uri={}, body={}", request.getMethod(), request.getRequestURI(), requestBody);
        } else {
            logger.info("Request: method={}, uri={}, body=Non-JSON content", request.getMethod(), request.getRequestURI());
        }
    }

    private void logResponse(ContentCachingResponseWrapper response) throws IOException {
        String responseBody = new String(response.getContentAsByteArray());
        logger.info("Response: status={}, body={}", response.getStatus(), responseBody);
        response.copyBodyToResponse(); // Ensure the response body is sent to the client

        String contentType = response.getContentType();
        if (contentType != null && contentType.contains("application/json")) {
            String requestBody = new String(response.getContentAsByteArray());
            logger.info("Response: status={}, body={}", response.getStatus(), responseBody);
        } else {
            logger.info("Request: method={}, uri={}, body=Non-JSON content");
        }
    }
}
