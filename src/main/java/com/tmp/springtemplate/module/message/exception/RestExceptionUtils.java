package com.tmp.springtemplate.module.message.exception;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.tmp.springtemplate.module.message.exception.type.BaseException;
import com.tmp.springtemplate.module.message.exception.type.ValidationException;
import com.tmp.springtemplate.module.message.model.ErrorResponse;
import com.tmp.springtemplate.module.message.model.Message;
import com.tmp.springtemplate.module.message.model.Severity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;


@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class RestExceptionUtils {


    protected static ValidationException mapToValidationException(Class<?> targetType, List<Reference> pathList, String message) {

        if (Objects.isNull(targetType)) {
            return new ValidationException(RestExceptionMessageCode.DEFAULT_VALIDATION_ERROR.getCode(), message, new Message[0]);
        } else {
            String formattedMessage;
            if (targetType.isEnum()) {
                formattedMessage = getEnumMessage(targetType);
                return new ValidationException(RestExceptionMessageCode.ENUM_FORMAT_ERROR.getCode(), formattedMessage, new Message[0]);
            } else if (!CollectionUtils.isEmpty(pathList)) {
                formattedMessage = getRequestBodyMessage(targetType, pathList);
                return new ValidationException(RestExceptionMessageCode.REQUEST_FORMAT_ERROR.getCode(), formattedMessage, new Message[0]);
            } else {
                return new ValidationException(RestExceptionMessageCode.DEFAULT_VALIDATION_ERROR.getCode(), message, new Message[0]);
            }
        }
    }

    protected static ValidationException createInvalidRequestBodyException() {

        return new ValidationException(RestExceptionMessageCode.INVALID_REQUEST_BODY.getCode(), RestExceptionMessageCode.INVALID_REQUEST_BODY.getMessage(), new Message[0]);
    }

    protected static String getEnumMessage(Class<?> targetType) {

        final String validOptions = new StringBuilder().append("[").append(StringUtils.join(targetType.getEnumConstants(), ", ")).append("]").toString();
        return String.format(RestExceptionMessageCode.ENUM_FORMAT_ERROR.getMessage(), validOptions);
    }

    protected static String getRequestBodyMessage(Class<?> targetType, List<Reference> pathList) {

        final String fieldName = (pathList.get(pathList.size() - 1)).getFieldName();
        return String.format(RestExceptionMessageCode.BAD_FORMAT_ERROR.getMessage(), fieldName, targetType);
    }

    protected static List<Message> getFieldErrorsAsMessageArray(Exception ex, Collection<FieldError> fieldErrors) {

        final List<Message> messages = new ArrayList<>();
        final Iterator iter = fieldErrors.iterator();

        while (iter.hasNext()) {
            final FieldError fieldError = (FieldError) iter.next();
            if (ex instanceof BindException) {
                final Class<?> clazz = ((BindException) ex).getFieldType(fieldError.getField());
                if (Objects.nonNull(clazz) && clazz.isEnum()) {
                    final String formattedMessage = getEnumMessage(clazz);
                    messages.add(new Message(Severity.ERROR, RestExceptionMessageCode.ENUM_FORMAT_ERROR.getCode(), formattedMessage));
                } else {
                    messages.add(new Message(Severity.ERROR, getFieldErrorCode(fieldError.getCode()), fieldError.getField() + " " + fieldError.getDefaultMessage()));
                }
            } else {
                messages.add(new Message(Severity.ERROR, getFieldErrorCode(fieldError.getCode()), fieldError.getField() + " " + fieldError.getDefaultMessage()));
            }
        }

        return messages;
    }

    protected static String getFieldErrorCode(String code) {

        return RestExceptionMessageCode.DEFAULT_VALIDATION_ERROR.getCode() + (StringUtils.isNotBlank(code) ? "." + code : "");
    }

    protected static String decodePath(WebRequest request) {

        try {
            return URLDecoder.decode(((ServletWebRequest) request).getRequest().getRequestURI(), StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException ex) {
            log.error(ex.getMessage());
            return "";
        }
    }

    protected static ResponseEntity<Object> createGenericResponseEntityForError(HttpStatus httpCode, Throwable ex) {

        return createGenericResponseEntityForError(httpCode, ex, null, null, null);
    }

    protected static ResponseEntity<Object> createGenericResponseEntityForError(HttpStatus httpCode, Throwable ex, String exceptionCode, String exceptionMessage, List<Message> messages) {

        log.error(ex.getClass().getName(), ex);
        final ErrorResponse response = createErrorResponseForResponseEntity(ex, exceptionCode, exceptionMessage, messages);
        return new ResponseEntity<>(response, httpCode);
    }

    protected static ResponseEntity<ErrorResponse> createResponseEntityForError(HttpStatus httpCode, Throwable ex) {

        return createResponseEntityForError(httpCode, ex, null, null, null);
    }

    protected static ResponseEntity<ErrorResponse> createResponseEntityForError(HttpStatus httpCode, Throwable ex, String exceptionCode, String exceptionMessage, List<Message> messages) {

        log.error(ex.getClass().getName(), ex);
        final ErrorResponse response = createErrorResponseForResponseEntity(ex, exceptionCode, exceptionMessage, messages);
        return new ResponseEntity<>(response, httpCode);
    }

    protected static ErrorResponse createErrorResponseForResponseEntity(Throwable ex, String exceptionCode, String exceptionMessage, List<Message> messages) {

        ErrorResponse response = null;

        if (StringUtils.isNotBlank(exceptionCode)) {
            response = RestExceptionUtils.createErrorResponse(exceptionCode, exceptionMessage, messages);
        } else if (ex instanceof BaseException) {
            final BaseException baseEx = (BaseException) ex;
            response = RestExceptionUtils.createErrorResponse(baseEx.getCode(), baseEx.getMessage(), baseEx.getMessages());
        } else {
            response = RestExceptionUtils.createErrorResponse(RestExceptionMessageCode.UNKNOWN_EXCEPTION.getCode(), RestExceptionMessageCode.UNKNOWN_EXCEPTION.getMessage(), null);
        }

        return response;
    }

    private static ErrorResponse createErrorResponse(String code, String message, List<Message> messages) {

        final ErrorResponse response = new ErrorResponse();
        response.setCode(code);
        response.setMessage(message);
        response.setTimestamp(LocalDateTime.now());
        response.setMessages(messages);
        return response;
    }

}
