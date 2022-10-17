package com.tmp.springtemplate.module.openapi;

import com.tmp.springtemplate.module.openapi.model.Api;
import com.tmp.springtemplate.module.openapi.model.ApplicationConfig;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;


@Configuration
@AllArgsConstructor
// TODO CHECK LATER
// @Profile({ AppEnvironment.DEVELOPMENT, AppEnvironment.UAT, AppEnvironment.PREP })
public class GroupedOpenApiConfig {

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private final OpenApiCustomiser openApiCustomiser;


    @Bean
    GroupedOpenApi createGroupedOpenApi1() {

        if (applicationConfig.getApis().values().size() < 1) {
            return null;
        }

        return createGroupedOpenApi();
    }

    @Bean
    GroupedOpenApi createGroupedOpenApi2() {

        if (applicationConfig.getApis().values().size() < 2) {
            return null;
        }

        return createGroupedOpenApi();
    }

    @Bean
    GroupedOpenApi createGroupedOpenApi3() {

        if (applicationConfig.getApis().values().size() < 3) {
            return null;
        }

        return createGroupedOpenApi();
    }

    @Bean
    GroupedOpenApi createGroupedOpenApi4() {

        if (applicationConfig.getApis().values().size() < 4) {
            return null;
        }

        return createGroupedOpenApi();
    }

    @Bean
    GroupedOpenApi createGroupedOpenApi5() {

        if (applicationConfig.getApis().values().size() < 5) {
            return null;
        }

        return createGroupedOpenApi();
    }

    @Bean
    GroupedOpenApi createGroupedOpenApi6() {

        if (applicationConfig.getApis().values().size() < 6) {
            return null;
        }

        return createGroupedOpenApi();
    }

    GroupedOpenApi createGroupedOpenApi() {

        final List<Api> apis = applicationConfig.getApis().values().stream()
                .filter(api -> BooleanUtils.isNotTrue(api.getProcessApiFlag())).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(apis)) {
            if (apis.size() > 1) {
                apis.get(0).setProcessApiFlag(Boolean.TRUE);
            } else {
                applicationConfig.getApis().values().stream().forEach(api -> api.setProcessApiFlag(Boolean.FALSE));
            }

            final Api api = apis.get(0);
            return GroupedOpenApi
                    .builder()
                    .group(api.getGroupName())
                    .pathsToMatch(api.getPath())
                    .addOpenApiCustomiser(openApiCustomiser)
                    .build();
        }

        return null;
    }

}
