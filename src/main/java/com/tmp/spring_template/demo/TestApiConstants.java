package com.tmp.spring_template.demo;

import com.tmp.spring_template.rest_api.ApiConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Created on November 2022
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestApiConstants {

    public static final String API_PATH_TEST = "/test/api";

    public static final String API_PATH_TEST_V1 = API_PATH_TEST + ApiConstants.API_PATH_V1;

}
