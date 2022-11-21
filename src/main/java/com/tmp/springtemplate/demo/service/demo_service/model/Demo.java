package com.tmp.springtemplate.demo.service.demo_service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created on November, 2022
 */
@Getter
@Setter
@NoArgsConstructor
public class Demo {

    private Long id;

    private String text;

    private Boolean flag;

    private Long numValue;

    private LocalDate dateValue;

    private LocalDateTime datetimeValue;

}
