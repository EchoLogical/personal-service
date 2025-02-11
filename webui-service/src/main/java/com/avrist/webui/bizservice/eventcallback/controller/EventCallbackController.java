package com.avrist.webui.bizservice.eventcallback.controller;

import com.avrist.webui.global.exception.BusinessServiceValidationException;
import com.avrist.webui.bizservice.eventcallback.EventCallbackService;
import com.avrist.webui.bizservice.eventcallback.model.request.EventCallbackRequest;
import com.avrist.webui.bizservice.eventcallback.model.response.EventCallbackResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/event")
public class EventCallbackController {

    private final EventCallbackService eventCallbackService;

    @Autowired
    public EventCallbackController(EventCallbackService eventCallbackService) {
        this.eventCallbackService = eventCallbackService;
    }

    @PostMapping("")
    public ResponseEntity<EventCallbackResponse> event(
            @RequestBody EventCallbackRequest request) throws BusinessServiceValidationException {
        return ResponseEntity
                .ok()
                .body(eventCallbackService.execute(request));
    }

}
