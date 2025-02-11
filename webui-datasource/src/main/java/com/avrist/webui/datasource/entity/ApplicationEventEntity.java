package com.avrist.webui.datasource.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ApplicationErrorEvent")
public class ApplicationEventEntity implements Serializable {


    private static final long serialVersionUID = 425182586945191572L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private UUID id;
    @Column(name = "Service", nullable = false)
    private String service;
    @Column(name = "ErrorCode", nullable = false)
    private String errorCode;
    @Column(name = "ErrorMessage", nullable = false)
    private String errorMessage;
    @Column(name = "Severity", nullable = false)
    private String severity;
    @Column(name = "Timestamp", nullable = false)
    private LocalDateTime timestamp;
    @Column(name = "ErrorCode", nullable = false)
    private String stackTrace;
    @Column(name = "RequestId", nullable = false)
    private String requestId;
    @Column(name = "CreatedAt", nullable = false)
    private LocalDateTime createdAt;
}
