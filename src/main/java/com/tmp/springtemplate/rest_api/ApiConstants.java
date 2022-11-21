package com.tmp.springtemplate.rest_api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiConstants {

    public static final String API_PATH_V1 = "/v1";

    public static final String API_PATH_APP = "/app/api";

    public static final String API_PATH_MANAGEMENT = "/management/api";


    public static final String API_PATH_APP_V1 = API_PATH_APP + API_PATH_V1;

    public static final String API_PATH_MANAGEMENT_V1 = API_PATH_MANAGEMENT + API_PATH_V1;


}
