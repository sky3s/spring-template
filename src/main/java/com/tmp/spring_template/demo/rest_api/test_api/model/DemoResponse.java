package com.tmp.spring_template.demo.rest_api.test_api.model;

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
public class DemoResponse {

    private Long id;

    private String text;

    private Boolean flag;

    private Long numValue;

    private LocalDate dateValue;

    private LocalDateTime datetimeValue;

}
