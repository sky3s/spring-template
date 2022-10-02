package com.tmp.springtemplate.extension.messaging.message_key;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum InfoMessageKey {

    /**
     * Demo purposes Messages
     */
    DEMO_INFO("info.app.demo");


    private final String code;

}
