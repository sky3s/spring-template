package com.tmp.spring_template.module.openapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Header {

    private String key;

    private Boolean useAsSecuritySchema;

    private Boolean required;

    private String defaultValue;

    private String example;

    private String description;

}
