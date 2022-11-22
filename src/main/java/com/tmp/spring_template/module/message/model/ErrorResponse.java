package com.tmp.spring_template.module.message.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.tmp.spring_template.util.AppConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    private String message;

    @JsonFormat(pattern = AppConstants.DEFAULT_DATE_TIME_FORMAT)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timestamp;

    private List<Message> messages;

    public ErrorResponse(String code, String message, LocalDateTime timestamp, Message... messages) {

        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
        this.messages = ArrayUtils.isNotEmpty(messages) ? Arrays.asList(messages) : null;
    }

}
