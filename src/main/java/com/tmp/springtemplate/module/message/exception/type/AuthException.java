package com.tmp.springtemplate.module.message.exception.type;

import com.tmp.springtemplate.module.message.model.Message;

import java.io.Serializable;


public class AuthException extends BaseException implements Serializable {

    private static final long serialVersionUID = 1L;

    public AuthException(String code, String message, Message... messages) {
        super(code, message, messages);
    }
}
