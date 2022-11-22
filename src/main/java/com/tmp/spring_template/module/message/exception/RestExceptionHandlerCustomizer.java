package com.tmp.spring_template.module.message.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.tmp.spring_template.module.message.exception.type.ValidationException;
import com.tmp.spring_template.module.message.model.Message;
import com.tmp.spring_template.module.message.model.Severity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.List;


@Slf4j
@AllArgsConstructor
public class RestExceptionHandlerCustomizer extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return RestExceptionUtils.createGenericResponseEntityForError(HttpStatus.METHOD_NOT_ALLOWED, ex, RestExceptionMessageCode.METHOD_NOT_ALLOWED_ERROR.getCode(), ex.getMessage(), null);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        log.error(ex.getClass().getName(), ex);
        final Throwable cause = ExceptionUtils.getRootCause(ex);
        final String rootCauseMessage = ExceptionUtils.getRootCauseMessage(ex);
        ValidationException validationException;

        if (cause instanceof InvalidFormatException) {
            final InvalidFormatException formatException = (InvalidFormatException) cause;
            validationException = RestExceptionUtils.mapToValidationException(formatException.getTargetType(), formatException.getPath(), rootCauseMessage);
        } else if (cause instanceof InvalidTypeIdException) {
            final InvalidTypeIdException formatException = (InvalidTypeIdException) cause;
            validationException = RestExceptionUtils.mapToValidationException(formatException.getTargetType(), formatException.getPath(), rootCauseMessage);
        } else {
            validationException = RestExceptionUtils.createInvalidRequestBodyException();
        }

        return RestExceptionUtils.createGenericResponseEntityForError(HttpStatus.PRECONDITION_FAILED, validationException);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return handleArgumentException(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return handleArgumentException(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return handleServletRequestBindingExceptions(ex, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return handleServletRequestBindingExceptions(ex, headers, HttpStatus.BAD_REQUEST, request);
    }

    protected ResponseEntity<Object> handleArgumentException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        log.error(ex.getClass().getName(), ex);
        final List<Message> messages = RestExceptionUtils.getFieldErrorsAsMessageArray(ex, ex.getFieldErrors());
        return RestExceptionUtils.createGenericResponseEntityForError(HttpStatus.PRECONDITION_FAILED, ex, RestExceptionMessageCode.DEFAULT_VALIDATION_ERROR.getCode(), RestExceptionMessageCode.DEFAULT_VALIDATION_ERROR.getMessage(), messages);
    }

    private ResponseEntity<Object> handleServletRequestBindingExceptions(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        final String errorUrl = RestExceptionUtils.decodePath(request);
        final String errorDetail = errorDetail(ex, errorUrl);
        log.error(errorDetail);

        final String clientMessage = StringUtils.substringAfter(errorDetail, "##");
        final Message message = new Message(Severity.ERROR, String.valueOf(status.value()), clientMessage);
        final List<Message> messages = Arrays.asList(message);

        return RestExceptionUtils.createGenericResponseEntityForError(status, ex, String.valueOf(status.value()), RestExceptionMessageCode.PARAMETER_BINDING_ERROR.getMessage(), messages);
    }

    private String errorDetail(ServletRequestBindingException ex, String errorUrl) {

        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getClass().getName());
        builder.append(" ## ");
        builder.append(ex.getMessage());
        builder.append(", PATH: ");
        builder.append(errorUrl);
        return builder.toString();
    }

}

