package com.tmp.springtemplate.demo.rest_api.test_api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created on November, 2022
 */
@Getter
@Setter
@NoArgsConstructor
public class QueryDemoResponse {

    private List<DemoResponse> result;

}
