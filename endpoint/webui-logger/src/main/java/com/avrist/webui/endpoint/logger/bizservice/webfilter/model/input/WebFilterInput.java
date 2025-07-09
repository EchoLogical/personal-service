package com.avrist.webui.endpoint.logger.bizservice.webfilter.model.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebFilterInput {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Object handler;
}
