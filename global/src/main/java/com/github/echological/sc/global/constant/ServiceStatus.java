package com.github.echological.sc.global.constant;

import lombok.Getter;

public enum ServiceStatus {

    SUCCESS("SUCCESS", "SRV-A0001"),
    OK("OK", "SRV-A0002"),
    REGISTERED("REGISTERED", "SRV-A0003"),
    INTERRUPTED("INTERRUPTED", "SRV-X0002"),
    FAILED("FAILED", "SRV-X0003"),
    CONNECTION_FAILED("CONNECTION_FAILED", "SRV-X0004"),
    SERVICE_NOT_FOUND("SERVICE_NOT_FOUND", "SRV-X0005"),
    DATA_NOT_FOUND("NOT_FOUND", "SRV-X0006"),
    INVALID_ARGUMENT("INVALID_ARGUMENT", "SRV-X0007"),
    MESSAGE_NOT_READABLE("MESSAGE_NOT_READABLE", "SRV-X0008"),
    NO_HANDLER_FOUND("NO_HANDLER_FOUND", "SRV-X0009"),
    UNAUTHORIZED("UNAUTHORIZED", "SRV-X0010"),
    UNSUPPORTED_MEDIA_TYPE("UNSUPPORTED_MEDIA_TYPE", "SRV-X0011"),
    UNAUTHORIZED_REDIRECT("UNAUTHORIZED_REDIRECT", "SRV-X0012"),
    ERROR("ERROR", "SRV-X0001");

    @Getter
    String status;
    @Getter
    String code;

    ServiceStatus(String status, String code) {
        this.status = status;
        this.code = code;
    }
}
