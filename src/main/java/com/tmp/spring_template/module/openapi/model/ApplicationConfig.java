package com.tmp.spring_template.module.openapi.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Getter
@Setter
@Component
@ConfigurationProperties("app")
public class ApplicationConfig {

    private String name;

    private List<String> domains;

    private String version;

    private Map<String, SecurityHeader> commonSecurityHeaders;

    private Map<String, Header> commonHeaders;

    private List<String> customPaths;

    private Map<String, Api> apis;

}