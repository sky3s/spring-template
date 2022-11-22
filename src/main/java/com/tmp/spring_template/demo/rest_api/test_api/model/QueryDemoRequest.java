package com.tmp.spring_template.demo.rest_api.test_api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

/**
 * Created on November, 2022
 */
@Getter
@Setter
@NoArgsConstructor
public class QueryDemoRequest {

    @Schema(required = true, description = "Send true if nativeQuery requested, default: false", defaultValue = "false", allowableValues = { "true", "false" })
    private Boolean useNativeQuery;

    @Nullable
    @Schema(required = false, description = "Send true if only records which flagged as true requested, default: false", defaultValue = "", allowableValues = { "true", "false" })
    private Boolean flag;

}
