package com.avrist.webui.app.interceptor;

import com.avrist.webui.app.service.webfilter.WebFilterService;
import com.avrist.webui.app.service.webfilter.model.input.WebFilterInput;
import com.avrist.webui.global.constant.AVRStatus;
import com.avrist.webui.global.exception.BusinessServiceValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class HttpInterceptor implements HandlerInterceptor {

    private final WebFilterService webFilterService;

    @Autowired
    public HttpInterceptor(WebFilterService webFilterService) {
        this.webFilterService = webFilterService;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
        if(webFilterService.execute(WebFilterInput.builder()
                        .request(request)
                        .response(response)
                        .handler(handler)
                .build())) return true;

        throw new BusinessServiceValidationException(
                AVRStatus.UNAUTHORIZED.getCode(),
                AVRStatus.UNAUTHORIZED.getStatus(),
                HttpStatus.UNAUTHORIZED,
                Stream.of("Access Denied.").collect(Collectors.toList()));
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
       // Implement logic
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Implement logic
    }
}
