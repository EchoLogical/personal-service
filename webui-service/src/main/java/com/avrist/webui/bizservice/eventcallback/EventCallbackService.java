package com.avrist.webui.bizservice.eventcallback;

import com.avrist.webui.datasource.entity.ApplicationEventEntity;
import com.avrist.webui.datasource.repository.ApplicationEventRepository;
import com.avrist.webui.global.exception.BusinessServiceValidationException;
import com.avrist.webui.bizservice.BusinessService;
import com.avrist.webui.bizservice.eventcallback.model.request.EventCallbackRequest;
import com.avrist.webui.bizservice.eventcallback.model.response.EventCallbackResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class EventCallbackService implements BusinessService<EventCallbackRequest, EventCallbackResponse> {

    private final ApplicationEventRepository applicationEventRepository;

    @Autowired
    public EventCallbackService(ApplicationEventRepository applicationEventRepository) {
        this.applicationEventRepository = applicationEventRepository;
    }

    @Override
    public EventCallbackResponse execute(EventCallbackRequest input) throws BusinessServiceValidationException {
        LocalDateTime timestamp;

        try {
            var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            timestamp = LocalDateTime.parse(input.getTimestamp(), formatter);
        }catch (Exception e){
            timestamp = LocalDateTime.now();
        }

        var data = ApplicationEventEntity.builder()
                .id(UUID.randomUUID())
                .service(input.getService())
                .errorCode(input.getErrorCode())
                .errorMessage(input.getErrorMessage())
                .severity(input.getSeverity())
                .timestamp(timestamp)
                .stackTrace(input.getStackTrace())
                .requestId(input.getRequestId())
                .createdAt(LocalDateTime.now())
                .build();

        applicationEventRepository.save(data);

        return EventCallbackResponse.builder()
                .id(data.getId().toString())
                .build();
    }
}
