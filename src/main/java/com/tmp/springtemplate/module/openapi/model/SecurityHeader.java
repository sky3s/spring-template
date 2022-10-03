package com.tmp.springtemplate.module.openapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecurityHeader {

    private String key;

    private String name;

    private String example;

    private String description;

}