package com.tmp.spring_template.module.message.exception.type;

import com.tmp.spring_template.module.message.model.Message;

import java.io.Serializable;


public class ExternalServiceException extends BaseException implements Serializable {

    private static final long serialVersionUID = 1L;

    public ExternalServiceException(String code, String message, Message... messages) {
        super(code, message, messages);
    }
}
