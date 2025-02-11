package com.avrist.webui.bizservice.eventcallback.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventCallbackRequest {
    private String errorCode;
    private String service;
    private String errorMessage;
    private String severity;
    private String timestamp;
    private String stackTrace;
    private String requestId;
}
