package com.tmp.spring_template.demo.service.demo_service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

/**
 * Created on November 2022
 */
@Getter
@Setter
@NoArgsConstructor
public class QueryDemoInput {

    @NotNull
    private Boolean useNativeQuery;

    @Nullable
    private Boolean flag;

}
