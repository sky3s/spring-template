package com.tmp.springtemplate.module.message.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum RestExceptionMessageCode {

    UNKNOWN_EXCEPTION("exception.message.unknown", "Unknown exception occurred."),
    UNKNOWN_EXCEPTION_TYPE("", "Unknown exception type"),

    PARAMETER_BINDING_ERROR("", "Parameter binding exception occurred."),
    METHOD_NOT_ALLOWED_ERROR("exception.message.method_not_allowed", ""),

    DEFAULT_VALIDATION_ERROR("validation.message.default", "A validation error occurred."),

    ENUM_FORMAT_ERROR("validation.message.invalid_enum_value", "Invalid enum value! Valid values: %s"),
    INVALID_REQUEST_BODY("validation.message.invalid_request_body", "Invalid request value!"),
    REQUEST_FORMAT_ERROR("validation.message.request_format_error", ""),
    BAD_FORMAT_ERROR("", "Bad Format: %s - %s");


    private String code;

    private String message;

}
