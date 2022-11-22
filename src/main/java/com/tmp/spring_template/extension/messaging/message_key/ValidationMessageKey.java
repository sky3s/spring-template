package com.tmp.spring_template.extension.messaging.message_key;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ValidationMessageKey {

    /**
     * Demo purposes Messages
     */
    DEMO_VALIDATION("validation.app.demo");


    private final String code;

}
