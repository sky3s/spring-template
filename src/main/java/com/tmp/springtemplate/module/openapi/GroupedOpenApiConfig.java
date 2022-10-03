package com.tmp.springtemplate.module.openapi;

import com.tmp.springtemplate.module.openapi.model.Api;
import com.tmp.springtemplate.module.openapi.model.ApplicationConfig;
import lombok.AllArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;


@Configuration
@AllArgsConstructor
// TODO CHECK LATER
// @Profile({ AppEnvironment.DEVELOPMENT, AppEnvironment.UAT, AppEnvironment.PREP })
public class GroupedOpenApiConfig {

    private final ApplicationConfig applicationConfig;

    private final OpenApiCustomiser openApiCustomiser;


    @Bean
    GroupedOpenApi addApi0() {

        return createGroupedOpenApi(0);
    }

    @Bean
    GroupedOpenApi addApi1() {

        return createGroupedOpenApi(1);
    }

    @Bean
    GroupedOpenApi addApi2() {

        return createGroupedOpenApi(2);
    }

    @Bean
    GroupedOpenApi addApi3() {

        return createGroupedOpenApi(3);
    }

    @Bean
    GroupedOpenApi addApi4() {

        return createGroupedOpenApi(4);
    }

    @Bean
    GroupedOpenApi addApi5() {

        return createGroupedOpenApi(5);
    }

    private GroupedOpenApi createGroupedOpenApi(int apiIndex) {

        final List<Api> apis = applicationConfig.getApis().values().stream().collect(Collectors.toList());
        final int apiCount = apis.size();

        if (apiIndex >= apiCount) {
            return null;
        }

        final Api api = apis.get(apiIndex);

        return GroupedOpenApi
                .builder()
                .group(api.getGroupName())
                .pathsToMatch(api.getPath())
                .addOpenApiCustomiser(openApiCustomiser)
                .build();
    }

}
