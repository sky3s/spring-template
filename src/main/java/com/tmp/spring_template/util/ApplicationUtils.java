package com.tmp.spring_template.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationUtils {

    private static boolean isSpecificEnvironment(Environment environment, AppEnvironment env) {

        for (String activeProfile : environment.getActiveProfiles()) {
            if (StringUtils.startsWithIgnoreCase(activeProfile, env.getCode())) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSpecificEnvironment(Environment environment, String key) {

        for (String activeProfile : environment.getActiveProfiles()) {
            if (StringUtils.startsWithIgnoreCase(activeProfile, key)) {
                return true;
            }
        }
        return false;
    }

}