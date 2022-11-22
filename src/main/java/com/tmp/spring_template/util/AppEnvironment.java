package com.tmp.spring_template.util;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum AppEnvironment {

    DEVELOPMENT("dev"),
    UAT("uat"),
    PREP("prep"),
    PRODUCTION("prod");


    private String code;

}
