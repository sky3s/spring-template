package com.tmp.spring_template.module.message.exception;

import com.tmp.spring_template.module.message.exception.type.AuthException;
import com.tmp.spring_template.module.message.exception.type.ExternalServiceException;
import com.tmp.spring_template.module.message.exception.type.ValidationException;
import com.tmp.spring_template.module.message.model.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


/**
 * CREATE_PROJECT: INITIAL
 * Add additional custom exception handle methods with @ExceptionHandler annotation
 */
@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class RestExceptionHandler extends RestExceptionHandlerCustomizer {

    @ExceptionHandler({Throwable.class})
    public static ResponseEntity<ErrorResponse> handleUnknownException(Throwable ex, WebRequest request) {

        return RestExceptionUtils.createResponseEntityForError(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    @ExceptionHandler({AuthException.class})
    public final ResponseEntity<ErrorResponse> handleAuthException(AuthException ex, WebRequest request) {

        return RestExceptionUtils.createResponseEntityForError(HttpStatus.UNAUTHORIZED, ex);
    }

    @ExceptionHandler({ValidationException.class})
    public final ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex, WebRequest request) {

        return RestExceptionUtils.createResponseEntityForError(HttpStatus.PRECONDITION_FAILED, ex);
    }

    @ExceptionHandler({ExternalServiceException.class})
    public final ResponseEntity<ErrorResponse> handleExternalServiceException(ExternalServiceException ex, WebRequest request) {

        return RestExceptionUtils.createResponseEntityForError(HttpStatus.SERVICE_UNAVAILABLE, ex);
    }

}