package com.tmp.springtemplate.rest_api.test_api;

import com.tmp.springtemplate.extension.messaging.message_key.ExceptionMessageKey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * TODO CLEAN: TEST
 */
@Getter
@Setter
@NoArgsConstructor
public class RequestModel {

    private String strField;

    @NonNull
    private ExceptionMessageKey key;

}
