package com.tmp.spring_template.extension.messaging;

import com.tmp.spring_template.extension.messaging.message_key.ExceptionMessageKey;
import com.tmp.spring_template.extension.messaging.message_key.InfoMessageKey;
import com.tmp.spring_template.extension.messaging.message_key.ValidationMessageKey;
import com.tmp.spring_template.module.message.MessageUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;


@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageHelper {

    public static String getMessage(ExceptionMessageKey messageKey, Object... args) {
        return MessageUtils.getMessage(messageKey.getCode(), args);
    }

    public static String getMessage(ExceptionMessageKey messageKey, Locale locale, Object... args) {
        return MessageUtils.getMessage(messageKey.getCode(), locale, args);
    }


    public static String getMessage(ValidationMessageKey messageKey, Object... args) {
        return MessageUtils.getMessage(messageKey.getCode(), args);
    }

    public static String getMessage(ValidationMessageKey messageKey, Locale locale, Object... args) {
        return MessageUtils.getMessage(messageKey.getCode(), locale, args);
    }


    public static String getMessage(InfoMessageKey messageKey, Object... args) {
        return MessageUtils.getMessage(messageKey.getCode(), args);
    }

    public static String getMessage(InfoMessageKey messageKey, Locale locale, Object... args) {
        return MessageUtils.getMessage(messageKey.getCode(), locale, args);
    }

}
