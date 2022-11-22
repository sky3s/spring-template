package com.tmp.spring_template.module.message.exception;

import com.tmp.spring_template.module.message.exception.type.AuthException;
import com.tmp.spring_template.module.message.exception.type.BaseException;
import com.tmp.spring_template.module.message.exception.type.ExternalServiceException;
import com.tmp.spring_template.module.message.exception.type.ValidationException;
import com.tmp.spring_template.module.message.model.Message;
import com.tmp.spring_template.module.message.MessageUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;


@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionHelper {


    public static AuthException newAuthException(String code, Object... args) {

        return newException(AuthException.class, code, args);
    }

    public static ValidationException newValidationException(String code, Object... args) {

        return newException(ValidationException.class, code, args);
    }

    public static ExternalServiceException newExternalServiceException(String code, Object... args) {

        return newException(ExternalServiceException.class, code, args);
    }


    public static AuthException newAuthException(String code, Locale locale, Object... args) {

        return newException(AuthException.class, code, locale, args);
    }

    public static ValidationException newValidationException(String code, Locale locale, Object... args) {

        return newException(ValidationException.class, code, locale, args);
    }

    public static ExternalServiceException newExternalServiceException(String code, Locale locale, Object... args) {

        return newException(ExternalServiceException.class, code, locale, args);
    }


    private static <T extends BaseException> T newException(Class<T> exceptionType, String code, Object... args) {

        return newException(exceptionType, code, MessageUtils.getLocale(), args);
    }

    private static <T extends BaseException> T newException(Class<T> exceptionType, String code, Locale locale, Object... args) {

        final String message = MessageUtils.getMessage(code, locale, args);

        if (AuthException.class.equals(exceptionType)) {
            return (T) new AuthException(code, message, new Message[0]);
        }

        if (ValidationException.class.equals(exceptionType)) {
            return (T) new ValidationException(code, message, new Message[0]);
        }

        if (ExternalServiceException.class.equals(exceptionType)) {
            return (T) new ExternalServiceException(code, message, new Message[0]);
        }

        throw new UnsupportedOperationException(RestExceptionMessageCode.UNKNOWN_EXCEPTION_TYPE.getMessage());
    }

}
